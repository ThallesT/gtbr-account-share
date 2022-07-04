package com.gtbr.gtbraccountshare.utils;

public class MessageUtils {

    public static String getCommandFromMessage(String fullMessage) {
        var commandWithPrefix = fullMessage.split(" ")[0];
        var command = commandWithPrefix.replace("?", "");

        return command;
    }
}
