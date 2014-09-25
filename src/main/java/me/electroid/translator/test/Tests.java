package me.electroid.translator.test;

import me.electroid.translator.ChatEvent;
import me.electroid.translator.Platform;
import me.electroid.translator.ServerPlayer;
import me.electroid.translator.Translator;

public class Tests {
    public static final String TEST_MESSAGE = "Ik ging naar huis omdat ik ziek was.";
    
    public static void main(String... args) {
        Platform p = new TestPlatform();
        new Translator(p).onEnable(); //create instance
        
        TestPlayer sender = new TestPlayer("Me", "nl");
                
        ServerPlayer recipient1 = p.getPlayer("Bob"); //locale: en
        ServerPlayer recipient2 = new TestPlayer("GermanPlayer123", "de");
        ServerPlayer recipient3 = new TestPlayer("My_Friend", "nl"); //to test caching of translations
        ServerPlayer recipient4 = p.getPlayer("Bobs_Friend"); //no multiple requests
        
        ChatEvent event = new ChatEvent(TEST_MESSAGE, sender, recipient1, recipient2, recipient3, recipient4);
        
        event.getMessage().translateFor(recipient1, "<%s> %s"); //make a request for English translation
        
        try { 
            System.out.println("Sleeping");
            Thread.sleep(1000*3);
            System.out.println("Done.");
        } catch (Exception ex) {} //sleep for 3 seconds, en translation should still print
        
        event.getMessage().translateFor(recipient2, "<%s> %s");
        event.getMessage().translateFor(recipient4, "<%s> %s"); //should be instant, because its already fetched
        
        System.out.println("This should be ran before the DE translation because fetching translations is done on a different thread");
    }
}
