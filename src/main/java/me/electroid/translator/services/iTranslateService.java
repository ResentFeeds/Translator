package me.electroid.translator.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.electroid.translator.Translator;
import me.electroid.translator.translating.TranslationRequest;
import me.electroid.translator.translating.TranslatorService;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class iTranslateService implements TranslatorService {
    private static final String API_URL = "http://itranslate4.eu/api/Translate?auth=%s&src=%s&trg=%s&dat=%s";

    @Override
    public TranslationRequest requestTranslation(String message, String fromLanguage, String toLanguage, TranslationResultHandler resultHandler) {
        final TranslationRequest request = new TranslationRequest(message, fromLanguage, toLanguage, resultHandler);

        Runnable r = new Runnable() {
            @Override
            public void run() {
                handleTranslationRequest(request);
            }
        };
        new Thread(r).start();
        
        return request;
    }

    private void handleTranslationRequest(TranslationRequest request) {
        try {
            URL requestUrl = new URL(String.format(API_URL, getAuthKey(), request.getSourceLanguage(), request.getTargetLanguage(), request.getMessage().replace(" ", "+")));
            HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) response.append(inputLine);
            in.close();

            JsonObject resultRoot = new JsonParser().parse(response.toString()).getAsJsonObject();
            JsonArray translations = resultRoot.getAsJsonArray("dat");

            StringBuilder translation = new StringBuilder();
            for (JsonElement e : translations) {
                JsonArray lines = e.getAsJsonObject().getAsJsonArray("text");
                for (JsonElement line : lines) translation.append(line.getAsString()).append("\n");
            }

            request.invokeHandler(translation.substring(0, translation.length()-2)); //Strip last \n
        } catch (Exception e) {
            e.printStackTrace();
            request.invokeHandler("Unable to translate: "+e.getMessage());
        }
    }

    private String getAuthKey() {
        return Translator.getInstance().getPlatform().getConfiguration().getString("itranslate.auth-key");
    }
}
