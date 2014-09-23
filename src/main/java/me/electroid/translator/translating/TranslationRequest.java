package me.electroid.translator.translating;

import me.electroid.translator.translating.TranslatorService.TranslationResultHandler;

public class TranslationRequest {
    private String message;
    private String sourceLanguage;
    private String targetLanguage;
    private TranslationResultHandler resultHandler;
    
    public TranslationRequest(String message, String fromLanguage, String toLanguage, TranslationResultHandler resultHandler) {
        this.message = message;
        this.sourceLanguage = fromLanguage;
        this.targetLanguage = toLanguage;
        this.resultHandler = resultHandler;
    }
    
    /**
     * Invokes the {@link TranslationResultHandler}
     * @param translatedText
     */
    public void invokeHandler(String translatedText) {
        this.resultHandler.handleTranslationResult(message, sourceLanguage, targetLanguage, translatedText);
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
