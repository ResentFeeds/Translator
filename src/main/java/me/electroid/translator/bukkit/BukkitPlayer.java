package me.electroid.translator.bukkit;

import me.electroid.translator.ServerPlayer;

import org.bukkit.entity.Player;

public class BukkitPlayer implements ServerPlayer {
    private Player player;
    
    public BukkitPlayer(Player p) {
        this.player = p;
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendRawMessage(String message) {
        player.sendRawMessage(message);
    }

    @Override
    public String getLocale() {
        //TODO: Reflection for locale
        return null;
    }

}
