package me.electroid.translator.bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        return getLocale(player);
    }

    @Override
    public String getChatName() {
        return player.getDisplayName();
    }
    
    public String getLocale(Player p) {
        try {
            Object ep = getMethod("getHandle", p.getClass()).invoke(p, (Object[]) null);
            Field f = ep.getClass().getDeclaredField("locale");
            f.setAccessible(true);
            String language = (String) f.get(ep);
            return language.split("_")[0]; //to change en_US -> en
        } catch (Exception ex) {
            ex.printStackTrace();
            return "en";
        }
    }

    private Method getMethod(String name, Class<?> clazz) {
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.getName().equals(name))
                return m;
        }
        return null;
    }
}
