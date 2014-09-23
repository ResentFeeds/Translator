package me.electroid.translator.bukkit;

import me.electroid.translator.Translator;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitTranslatorPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        BukkitPlatform platform = new BukkitPlatform(this);
        Bukkit.getPluginManager().registerEvents(platform, this);
        
        Translator inst = new Translator(platform);
        inst.onEnable();
    }
    
    @Override
    public void onDisable() {
        Translator.getInstance().onDisable();
    }
}
