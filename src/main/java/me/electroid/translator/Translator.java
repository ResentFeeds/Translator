package me.electroid.translator;

import java.util.Arrays;
import java.util.HashMap;

import me.electroid.translator.services.GoogleTranslateService;
import me.electroid.translator.services.iTranslateService;
import me.electroid.translator.translating.TranslatorService;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

/**
 * Main class for the plugin, is as abstract as possible to allow for multiple server implementations
 * @author molenzwiebel
 */
public class Translator {
    private static HashMap<String, Class<? extends TranslatorService>> translatorServices;
    static {
        translatorServices = Maps.newHashMap();

        translatorServices.put("itranslate", iTranslateService.class);
        translatorServices.put("googletranslate", GoogleTranslateService.class);
    }

    private static Translator instance;
    private Platform platform;
    private TranslatorService translator;

    public Translator(Platform p) {
        this.platform = p;

        String transl = p.getConfiguration().getString("translator");
        if (!translatorServices.containsKey(transl)) throw new RuntimeException("Cannot create translation service for "+transl+" because it does not exist (are you sure its spelled correctly?)");
        try {
            this.translator = translatorServices.get(transl).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot create translation service for "+transl, e);
        }
    }

    /**
     * Called when the plugin gets enabled
     */
    public void onEnable() {
        instance = this;
    }

    /**
     * Called when the plugin gets disabled
     */
    public void onDisable() {
        instance = null;
    }
    
    /**
     * Handles the actual chat
     */
    public void onChat(ChatEvent e) {
        if (Boolean.parseBoolean(getPlatform().getConfiguration().getString("enabled"))) {
            e.translateAll();
        }
    }

    /**
     * Gets the current {@link Translator} instance.
     * @return the instance
     */
    public static Translator getInstance() {
        if (instance == null) throw new IllegalStateException("Cannot get Translator instance before being enabled!");
        return instance;
    }

    /**
     * Gets the current platform that Translator is running on
     * @return
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Gets the current active translation service
     */
    public TranslatorService getTranslator() {
        return translator;
    }
    
    /**
     * Generates JSON for a hover message. Helping method
     */
    public static String generateHoverJSON(String text, String... hoverText) {
        StringBuilder retval = new StringBuilder();
        retval.append("{text:\""+text+"\",hoverEvent:{action:show_text,value:{text:\"");
        retval.append(Joiner.on("\n").join(hoverText));
        retval.append("\"}}}");
        return retval.toString();
    }
}
