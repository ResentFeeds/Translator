package me.electroid.translator;

/**
 * Basic interface required for Translator to interact with players. This class is here to add compat with more than one server implementation (Bukkit and Sponge).
 * @author molenzwiebel
 */
public interface ServerPlayer {
    /**
     * @return the player's name as shown in chat
     */
    public String getChatName();
    
    /**
     * Sends a message to the {@link ServerPlayer}
     * @param message the message to send
     */
    public void sendMessage(String message);
    
    /**
     * Sends a raw JSON message to the {@link ServerPlayer}
     * @param message the JSON message
     */
    public void sendRawMessage(String message);
    
    /**
     * Gets the current Minecraft locale that the {@link ServerPlayer} has configured.
     * @return the player's Minecraft locale
     */
    public String getLocale();
}
