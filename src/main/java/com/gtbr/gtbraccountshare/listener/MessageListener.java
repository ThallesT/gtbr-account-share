package com.gtbr.gtbraccountshare.listener;

import com.gtbr.gtbraccountshare.handler.CommandHandler;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class MessageListener extends ListenerAdapter {

    private static final Long idGTBRRole = 607326526312284162L;
    private static final String prefix = ">";

    private final CommandHandler commandHandler;

    public MessageListener() {
        this.commandHandler = new CommandHandler();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent messageReceivedEvent) {
        var message = messageReceivedEvent.getMessage().getContentRaw();
        if (!messageReceivedEvent.getAuthor().isBot()
                && messageReceivedEvent.getMember().getRoles().stream().anyMatch(role -> role.getIdLong() == idGTBRRole)
                && message.toLowerCase(Locale.ROOT).startsWith(prefix)
        ) {
            commandHandler.handle(messageReceivedEvent);
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        event.getJDA().getGuilds()
                .forEach(guild -> guild.findMembers(member -> member.getRoles()
                        .stream()
                        .anyMatch(role -> role.getIdLong() == idGTBRRole)));
    }
}
