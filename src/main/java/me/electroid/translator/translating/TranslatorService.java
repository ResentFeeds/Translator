package me.electroid.translator.translating;

/**
 * Represents a translating API (such as Google Translate, Bing) that is able to take in a String and convert it to the specified language.
 * @author molenzwiebel
 */
public interface TranslatorService {
    /**
     * Starts a request to the {@link TranslatorService} asking for the specified message to be translated
     * @param message the message to be translated
     * @param fromLanguage the source language
     * @param toLanguage the language that the message should be converted to
     * @param resultHandler the function that should be ran once a translation is complete
     */
    public TranslationRequest requestTranslation(String message, String fromLanguage, String toLanguage, TranslationResultHandler resultHandler);
    
    public interface TranslationResultHandler {
        /**
         * Called when a {@link TranslatorService} successfully translates a string.
         * @param sourceText the source text
         * @param sourceLanguage the source language
         * @param targetLanguage the target language
         * @param translatedText the translated text
         */
        public void handleTranslationResult(String sourceText, String sourceLanguage, String targetLanguage, String translatedText);
    }
}
