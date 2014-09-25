package me.electroid.translator;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Represents an event that gets called whenever someone chats.
 * @author molenzwiebel
 */
public class ChatEvent {
    private ChatMessage message;
    private ServerPlayer sender;
    private List<ServerPlayer> recipients;
    private boolean shouldCancelActualMessage = false;
    
    public ChatEvent(String msg, ServerPlayer sender, List<ServerPlayer> recipients) {
        this.message = new ChatMessage(msg, sender.getLocale(), sender);
        this.sender = sender;
        this.recipients = ImmutableList.copyOf(recipients);
    }
    
    public ChatEvent(String msg, ServerPlayer sender, ServerPlayer... recipients) {
        this(msg, sender, Arrays.asList(recipients));
    }
    
    /**
     * Translates and sends the message to every recipient
     */
    public void translateAll() {
        shouldCancelActualMessage = true; //cancel sending
        for (ServerPlayer recipient : recipients) {
            message.translateFor(recipient, Translator.getInstance().getPlatform().getConfiguration().getString("message-format"));
        }
    }

    /**
     * @return the message
     */
    public ChatMessage getMessage() {
        return message;
    }

    /**
     * @return the sender
     */
    public ServerPlayer getSender() {
        return sender;
    }

    /**
     * @return the recipients
     */
    public List<ServerPlayer> getRecipients() {
        return recipients;
    }
    
    /**
     * @return if the old message should be cancelled or not
     */
    public boolean shouldCancel() {
        return shouldCancelActualMessage;
    }
}
