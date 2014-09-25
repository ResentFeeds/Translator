package me.electroid.translator.translating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.electroid.translator.translating.TranslatorService.TranslationResultHandler;

public class TranslationRequest {
    private String message;
    private String sourceLanguage;
    private String targetLanguage;
    private List<TranslationResultHandler> resultHandlers;
    
    public TranslationRequest(String message, String fromLanguage, String toLanguage, TranslationResultHandler resultHandler) {
        this.message = message;
        this.sourceLanguage = fromLanguage;
        this.targetLanguage = toLanguage;
        this.resultHandlers = new ArrayList<TranslationResultHandler>(Arrays.asList(resultHandler));
    }
    
    /**
     * Invokes the {@link TranslationResultHandler}
     * @param translatedText
     */
    public void invokeHandler(String translatedText) {
        for (TranslationResultHandler r : resultHandlers)
            r.handleTranslationResult(message, sourceLanguage, targetLanguage, translatedText);
    }
    
    /**
     * Adds a result handler to the TranslationRequest
     * @param resH
     */
    public void addResultHandler(TranslationResultHandler resH) {
        resultHandlers.add(resH);
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return the sourceLanguage
     */
    public String getSourceLanguage() {
        return sourceLanguage;
    }

    /**
     * @return the targetLanguage
     */
    public String getTargetLanguage() {
        return targetLanguage;
    }
}
