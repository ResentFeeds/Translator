package me.electroid.translator;


/**
 * Main class for the plugin, is as abstract as possible to allow for multiple server implementations
 * @author molenzwiebel
 */
public class Translator {
    private static Translator instance;
    private Platform platform;
    
    public Translator(Platform p) {
        this.platform = p;
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
}
