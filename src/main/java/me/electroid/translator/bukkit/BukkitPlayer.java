package me.electroid.translator.bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import me.electroid.translator.ServerPlayer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BukkitPlayer implements ServerPlayer {
    private static String NMS_PATH = "net.minecraft.server." + (Bukkit.getServer() != null ? Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] : "UNKNOWN")+".";
    
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
        try {
            Object handle = getMethod("getHandle", player.getClass()).invoke(player, (Object[]) null);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            
            Constructor<?> chatPacketConstructor = Class.forName(NMS_PATH+"PacketPlayOutChat").getConstructor(Class.forName(NMS_PATH+"IChatBaseComponent"));
            Class<?> chatSerializer = Class.forName(NMS_PATH+"ChatSerializer");
            Method a = null;
            for (Method m : chatSerializer.getDeclaredMethods())
                if (m.getReturnType().equals(Class.forName(NMS_PATH+"IChatBaseComponent")) && m.getParameterTypes()[0].equals(String.class)) a = m;
            
            playerConnection.getClass().getMethod("sendPacket", Class.forName(NMS_PATH+"Packet")).invoke(playerConnection, chatPacketConstructor.newInstance(a.invoke(null, message)));
        } catch (Exception e) {
            e.printStackTrace();
            sendMessage(ChatColor.RED+"Sorry, an internal error occured!");
        }
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
