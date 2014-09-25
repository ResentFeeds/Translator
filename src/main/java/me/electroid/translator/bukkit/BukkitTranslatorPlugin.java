package me.electroid.translator.bukkit;

import java.util.List;

import me.electroid.translator.ChatEvent;
import me.electroid.translator.ServerPlayer;
import me.electroid.translator.Translator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

public class BukkitTranslatorPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        BukkitPlatform platform = new BukkitPlatform(this);
        Bukkit.getPluginManager().registerEvents(platform, this);
        Bukkit.getPluginManager().registerEvents(this, this);

        Translator inst = new Translator(platform);
        inst.onEnable();
    }

    @Override
    public void onDisable() {
        Translator.getInstance().onDisable();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        List<ServerPlayer> recipients = Lists.newArrayList();
        for (Player r : e.getRecipients()) recipients.add(Translator.getInstance().getPlatform().getPlayer(r.getName()));
        
        ChatEvent event = new ChatEvent(e.getMessage(), Translator.getInstance().getPlatform().getPlayer(e.getPlayer().getName()), recipients);
        Translator.getInstance().onChat(event);
        if (event.shouldCancel()) e.setCancelled(true);
    }
}
