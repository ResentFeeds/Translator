package me.electroid.translator.test;

import me.electroid.translator.ChatMessage;
import me.electroid.translator.Platform;
import me.electroid.translator.ServerPlayer;
import me.electroid.translator.Translator;

public class Tests {
    public static final String TEST_MESSAGE = "Ik ging naar huis omdat ik ziek was.";
    public static final String TEST_LANGUAGE = "nl";
    
    public static void main(String... args) {
        Platform p = new TestPlatform();
        new Translator(p).onEnable(); //create instance
        
        TestPlayer sender = new TestPlayer("Me", "nl");
        
        ChatMessage msg = new ChatMessage(TEST_MESSAGE, TEST_LANGUAGE, sender);
        
        ServerPlayer recipient1 = p.getPlayer("Bob"); //locale: en
        ServerPlayer recipient2 = new TestPlayer("GermanPlayer123", "de");
        
        msg.translateFor(recipient1, "<%s> TR: %s");
        msg.translateFor(recipient2, "<%s> TR: %s");
    }
}
