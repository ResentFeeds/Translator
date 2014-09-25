package me.electroid.translator.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import me.electroid.translator.translating.TranslationRequest;
import me.electroid.translator.translating.TranslatorService;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GoogleTranslateService implements TranslatorService {
    private static final String URL = "http://translate.google.com/translate_a/t?client=x&text=%s&sl=%s&tl=%s&ie=UTF-8";

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
            URL requestUrl = new URL(String.format(URL, request.getMessage().replace(" ", "+"), request.getSourceLanguage(), request.getTargetLanguage()));
            HttpURLConnection con = (HttpURLConnection) requestUrl.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) response.append(inputLine);
            in.close();
            
            JsonObject resultRoot = new JsonParser().parse(response.toString()).getAsJsonObject();
            JsonArray sentences = resultRoot.getAsJsonArray("sentences");
            StringBuilder translation = new StringBuilder();
            for (JsonElement sentence : sentences) {
                translation.append(sentence.getAsJsonObject().getAsJsonPrimitive("trans").getAsString()).append("\n");
            }            
            request.invokeHandler(translation.substring(0, translation.length()-2)); //Strip last \n
        } catch (Exception e) {
            e.printStackTrace();
            request.invokeHandler("Unable to translate: "+e.getMessage());
        }
    }
}
