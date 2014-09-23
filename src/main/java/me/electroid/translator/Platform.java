package me.electroid.translator;

/**
 * Represents a server platform
 * @author molenzwiebel
 */
public interface Platform {
    /**
     * @return the platform configuration handler
     */
    public ConfigurationProvider getConfiguration();
    
    /**
     * Gets the player with the provided name
     * @param name
     * @return
     */
    public ServerPlayer getPlayer(String name);
}
