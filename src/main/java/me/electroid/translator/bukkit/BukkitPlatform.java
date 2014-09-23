package me.electroid.translator.bukkit;

import java.util.HashMap;

import me.electroid.translator.ConfigurationProvider;
import me.electroid.translator.Platform;
import me.electroid.translator.ServerPlayer;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Maps;

public class BukkitPlatform implements Platform, Listener {
    private HashMap<String, BukkitPlayer> bukkitPlayers = Maps.newHashMap();
    private BukkitConfiguration config;
    
    public BukkitPlatform(Plugin p) {
        p.saveDefaultConfig();
        p.reloadConfig();
        
        this.config = new BukkitConfiguration(p.getConfig());
    }

    @Override
    public ConfigurationProvider getConfiguration() {
        return config;
    }

    @Override
    public ServerPlayer getPlayer(String name) {
        if (bukkitPlayers.containsKey(name)) return bukkitPlayers.get(name);
        BukkitPlayer p = new BukkitPlayer(Bukkit.getPlayer(name));
        bukkitPlayers.put(name, p);
        return p;
    }

    @EventHandler
    public void onPlayerLogoff(PlayerQuitEvent e) {
        if (bukkitPlayers.containsKey(e.getPlayer().getName())) bukkitPlayers.remove(e.getPlayer().getName());
    }
}
