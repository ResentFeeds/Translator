package me.electroid.translator;

/**
 * Basic interface for a settings file that can store and access strings.
 * @author molenzwiebel
 */
public interface ConfigurationProvider {
    /**
     * Gets the string value at the provided configuration path
     * @param path
     * @return
     */
    public String getString(String path);
    
    /**
     * Sets the provided string value for the provided path
     * @param path
     * @param value
     */
    public void setString(String path, String value);
}
