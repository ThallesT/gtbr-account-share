package com.gtbr.gtbraccountshare.handler;

import com.gtbr.gtbraccountshare.exception.CommandException;
import com.gtbr.gtbraccountshare.model.AccountShare;
import com.gtbr.gtbraccountshare.service.AccountShareService;
import com.gtbr.gtbraccountshare.service.RequestService;
import com.gtbr.gtbraccountshare.service.ThumbnailsService;
import com.gtbr.gtbraccountshare.utils.MessageUtils;
import com.gtbr.gtbraccountshare.utils.SleepUtils;
import com.gtbr.gtbraccountshare.utils.SpringUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

import static com.gtbr.gtbraccountshare.utils.Constants.ACCOUNT_SHARE_CHANNEL_ID;

public class CommandHandler {

    private final AccountShareService accountShareService;
    private final RequestService requestService;
    private final ThumbnailsService thumbnailsService;

    public CommandHandler() {
        this.accountShareService = SpringUtils.getBean(AccountShareService.class);
        this.requestService = SpringUtils.getBean(RequestService.class);
        this.thumbnailsService = SpringUtils.getBean(ThumbnailsService.class);
    }

    public void handle(MessageReceivedEvent messageReceivedEvent) {
        var fullMessage = messageReceivedEvent.getMessage().getContentRaw();
        var command = MessageUtils.getCommandFromMessage(fullMessage);
        var jda = messageReceivedEvent.getJDA();

        try {
            switch (command) {
                case "share" -> shareHandle(fullMessage, messageReceivedEvent);
                case "find" -> findHandle(fullMessage, messageReceivedEvent, jda);
                case "help" -> helpHandle(messageReceivedEvent);
                case "list" -> listHandle(messageReceivedEvent);
                case "delete" -> deleteHandle(fullMessage, messageReceivedEvent);
                case "update" -> updateHandle(fullMessage, messageReceivedEvent);
                case "setthumb" -> createThumbnail(fullMessage, messageReceivedEvent);
                default -> messageReceivedEvent.getChannel()
                        .sendMessage("Esse comando não foi reconhecido, para receber ajuda use o comando: ```?help```")
                        .queue(message -> SleepUtils.sleep(20, message, messageReceivedEvent));
            }
        } catch (RuntimeException exception) {
            MessageEmbed messageEmbed = new EmbedBuilder().setTitle("Erro!").setDescription(exception.getMessage()).build();

            messageReceivedEvent.getChannel().sendMessageEmbeds(messageEmbed)
                    .queue(message -> SleepUtils.sleep(10, message, messageReceivedEvent));
        }
    }

    private void shareHandle(String fullMessage, MessageReceivedEvent messageReceivedEvent) {
        String[] messageSplitted = fullMessage.split(" ");
        if (messageSplitted.length < 4)
            throw new CommandException("Precisa preencher com todos os dados. ?share <plataforma> " +
                    "<login> <senha> <temAutenticador?True:False>");
        accountShareService.createAccountShare(
                messageSplitted[2],
                messageSplitted[3],
                messageReceivedEvent.getAuthor().getId(),
                Boolean.getBoolean(messageSplitted[4]),
                messageSplitted[1]);
        messageReceivedEvent.getMessage().delete().queue();
        messageReceivedEvent.getChannel().sendMessage("A conta foi salva com sucesso!")
                .queue(message -> SleepUtils.sleep(1, message, messageReceivedEvent));

    }

    private void findHandle(String fullMessage, MessageReceivedEvent messageReceivedEvent, JDA jda) {
        String platform = fullMessage.split(" ")[1];
        AccountShare accountShare = accountShareService.findPlatform(platform);
        requestService.createRequest(accountShare, messageReceivedEvent);
        MessageEmbed messageEmbed = MessageUtils.buildBuscarMessage(accountShare,
                thumbnailsService.findThumbnail(accountShare.getPlatform()).getImageUrl());

        if (!messageReceivedEvent.getChannel().getId().equals(ACCOUNT_SHARE_CHANNEL_ID.toString()))
            messageReceivedEvent
                    .getChannel()
                    .sendMessage("As credenciais foram enviadas no chat "
                            + jda.getTextChannelById(ACCOUNT_SHARE_CHANNEL_ID).getAsMention()
                            + ", corra pois ela vai expirar em 20 segundos.")
                    .queue();
        jda.getTextChannelById(ACCOUNT_SHARE_CHANNEL_ID).sendMessageEmbeds(messageEmbed)
                .queue(message -> SleepUtils.sleep(20, message, messageReceivedEvent));
    }

    private void helpHandle(MessageReceivedEvent messageReceivedEvent) {
        MessageEmbed messageEmbed = MessageUtils.buildHelpMessage();
        messageReceivedEvent.getChannel().sendMessageEmbeds(messageEmbed)
                .queue(message -> SleepUtils.sleep(20, message, messageReceivedEvent));
    }

    private void listHandle(MessageReceivedEvent messageReceivedEvent) {
        List<AccountShare> accountShareList = accountShareService.findAll();
        MessageEmbed messageEmbed = MessageUtils.buildListMessage(accountShareList);
        messageReceivedEvent.getChannel().sendMessageEmbeds(messageEmbed)
                .queue(message -> SleepUtils.sleep(20, message, messageReceivedEvent));
    }

    private void deleteHandle(String fullMessage, MessageReceivedEvent messageReceivedEvent) {
        String platform = fullMessage.split(" ")[1];
        accountShareService.deletePlatform(platform, messageReceivedEvent.getAuthor().getId());
        messageReceivedEvent.getChannel().sendMessage("Plataforma deletada com sucesso!")
                .queue(message -> SleepUtils.sleep(5, message, messageReceivedEvent));
    }

    private void updateHandle(String fulllMessage, MessageReceivedEvent messageReceivedEvent) {
        try {
            String[] messageSplitted = fulllMessage.split(" ");
            accountShareService.updatePlatform(
                    messageSplitted[2],
                    messageSplitted[3],
                    messageSplitted[4],
                    messageReceivedEvent.getAuthor().getId(),
                    Boolean.getBoolean(messageSplitted[5]),
                    messageSplitted[1]);
            messageReceivedEvent.getChannel().sendMessage("Plataforma atualizada com sucesso!")
                    .queue(message -> SleepUtils.sleep(7, message, messageReceivedEvent));
        } catch (Exception e) {
            throw new CommandException("É necessário preencher os dados da seguinte forma: ?update <plataforma atual> <nova plataforma> <login> <senha> <temAutenticador?'true' ou 'false'>");

        }
    }

    private void createThumbnail(String fullMessage, MessageReceivedEvent messageReceivedEvent) {
        try {
            String platform = fullMessage.split(" ")[2];
            String imageUrl = fullMessage.split(" ")[1];
            thumbnailsService.createThumbnail(platform, imageUrl);
            messageReceivedEvent.getChannel().sendMessage("Thumbnail cadastrada com sucesso!")
                    .queue(message -> SleepUtils.sleep(7, message, messageReceivedEvent));
        } catch (Exception e) {
            throw new CommandException("Não foi possivel cadastrar a thumbnail!");
        }
    }
}