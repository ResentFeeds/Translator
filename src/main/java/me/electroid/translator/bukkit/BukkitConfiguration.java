package me.electroid.translator.bukkit;

import me.electroid.translator.ConfigurationProvider;

import org.bukkit.configuration.file.FileConfiguration;

public class BukkitConfiguration implements ConfigurationProvider {
    private FileConfiguration config;

    public BukkitConfiguration(FileConfiguration cfg) {
        this.config = cfg;
    }
    
    @Override
    public String getString(String path) {
        return config.getString(path);
    }

    @Override
    public void setString(String path, String value) {
        config.set(path, value);
    }
}
