package com.gtbr.gtbraccountshare.utils;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class SleepUtils {
    private SleepUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void sleep(Integer time, Message message, MessageReceivedEvent messageReceivedEvent) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            message.delete().queue();
            messageReceivedEvent.getMessage().delete().queue();
            Thread.currentThread().interrupt();
        }
        message.delete().queue();
        messageReceivedEvent.getMessage().delete().queue();
    }
}