package me.electroid.translator;

import java.util.HashMap;
import java.util.List;

import me.electroid.translator.translating.TranslationRequest;
import me.electroid.translator.translating.TranslatorService.TranslationResultHandler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Represents a chat message that can be translated
 * @author molenzwiebel
 */
public class ChatMessage {
    private String originalMessage;
    private String originalLanguage;
    private ServerPlayer sender;
    
    private HashMap<String, String> translations = Maps.newHashMap();
    private List<TranslationRequest> pendingTranslations = Lists.newArrayList(); //A list of pending translations, to prevent multiple requests for the same language
    
    public ChatMessage(String msg, String originalLanguage, ServerPlayer sender) {
        this.originalMessage = msg;
        this.originalLanguage = originalLanguage;
        this.sender = sender;
        
        this.translations.put(originalLanguage, originalMessage);
    }
    
    public void translateFor(final ServerPlayer player, final String messageFormat) {
        String playerLocale = player.getLocale();
        
        //If the message was already translated in the player's language
        if (translations.containsKey(playerLocale)) {
            sendTranslatedMessage(player, messageFormat, translations.get(playerLocale));
            return; //done here
        } else if (getPendingRequest(playerLocale) != null) { //if the translation is on its way
            getPendingRequest(playerLocale).addResultHandler(new TranslationResultHandler() {
                @Override
                public void handleTranslationResult(String sourceText, String sourceLanguage, String targetLanguage, String translatedText) { 
                    sendTranslatedMessage(player, messageFormat, translatedText);
                }
            });
        } else { //not translated yet, no request made either
            TranslationRequest req = Translator.getInstance().getTranslator().requestTranslation(originalMessage, originalLanguage, playerLocale, new TranslationResultHandler() {
                @Override
                public void handleTranslationResult(String sourceText, String sourceLanguage, String targetLanguage, String translatedText) {
                    translations.put(targetLanguage, translatedText);
                    pendingTranslations.remove(this);
                    
                    sendTranslatedMessage(player, messageFormat, translatedText);
                }
            });
            this.pendingTranslations.add(req);
        }
    }
    
    private void sendTranslatedMessage(ServerPlayer to, String format, String translation) {
        String messageToSend = String.format(format, sender.getChatName(), translation);
        to.sendRawMessage(messageToSend);
    }
    
    private TranslationRequest getPendingRequest(String lang) {
        for (TranslationRequest req : pendingTranslations) if (req.getTargetLanguage().equals(lang)) return req;
        return null;
    }
}
