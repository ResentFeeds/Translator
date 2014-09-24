package me.electroid.translator.test;

import me.electroid.translator.ServerPlayer;

public class TestPlayer implements ServerPlayer {
    private String name;
    private String locale;
    
    public TestPlayer(String name, String locale) {
        this.name = name;
        this.locale = locale;
    }

    @Override
    public void sendMessage(String message) {
        System.out.println("(Server -> "+name+"): "+message);
    }

    @Override
    public void sendRawMessage(String message) {
        sendMessage(message);
    }

    @Override
    public String getLocale() {
        return locale;
    }

    @Override
    public String getChatName() {
        return name;
    }
}
