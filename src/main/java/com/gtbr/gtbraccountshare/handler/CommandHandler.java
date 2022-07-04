package com.gtbr.gtbraccountshare.handler;

import com.gtbr.gtbraccountshare.model.AccountShare;
import com.gtbr.gtbraccountshare.service.AccountShareService;
import com.gtbr.gtbraccountshare.utils.MessageUtils;
import com.gtbr.gtbraccountshare.utils.SpringUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class CommandHandler {

    private final AccountShareService accountShareService;

    public CommandHandler() {
        this.accountShareService = SpringUtils.getBean(AccountShareService.class);
    }

    public void handle(MessageReceivedEvent messageReceivedEvent) {
        var fullMessage = messageReceivedEvent.getMessage().getContentRaw();
        var command = MessageUtils.getCommandFromMessage(fullMessage);
        var jda = messageReceivedEvent.getJDA();


        try {
            switch (command) {
                case "share" -> {
                    String[] messageSplitted = fullMessage.split(" ");
                    if (messageSplitted.length < 4)
                        throw new RuntimeException("Precisa preencher com todos os dados. ?share <plataforma> <login> <senha> <temAutenticador?True:False>");
                    accountShareService.createAccountShare(messageSplitted[2],
                            messageSplitted[3],
                            messageReceivedEvent.getAuthor().getId(),
                            Boolean.getBoolean(messageSplitted[4]),
                            messageSplitted[1]
                    );

                    messageReceivedEvent.getMessage().delete().queue();
                    messageReceivedEvent.getChannel().sendMessage("A conta foi salva com sucesso!").queue(message -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            message.delete().queue();
                        }
                        message.delete().queue();
                    });
                }
                case "buscar" -> {
                    String platform = fullMessage.split(" ")[1];
                    AccountShare accountShare = accountShareService.findPlatform(platform);

                    MessageEmbed messageEmbed = new EmbedBuilder()
                            .setTitle(accountShare.getPlatform())
                            .setDescription("Esta mensagem expira em 20 segundos.")
                            .setFooter("id: " + accountShare.getId())
                            .addField("Login", accountShare.getUsername(), true)
                            .addField("Password", accountShare.getPassword(), true)
                            .addBlankField(false)
                            .addField("Tem autenticador / 2FA", accountShare.isAuthenticator() ? "Sim" : "Nao", true)
                            .addField("Dono da conta", jda.getUserById(accountShare.getOwner()).getAsMention(), true)
                            .setColor(Color.BLUE)
                            .build();

                    if (!messageReceivedEvent.getChannel().getId().equals("993348110652805182"))
                        messageReceivedEvent
                                .getChannel()
                                .sendMessage("As credenciais foram enviadas no chat " + jda.getTextChannelById(993348110652805182L).getAsMention() + ", corra pois ela vai expirar em 20 segundos.")
                                .queue();

                    jda.getTextChannelById(993348110652805182L).sendMessageEmbeds(messageEmbed).queue(message -> {
                        try {
                            Thread.sleep(20000);
                        } catch (InterruptedException e) {
                            message.delete().queue();
                        }
                        messageReceivedEvent.getMessage().delete().queue();
                        message.delete().queue();
                    });

                }
                case "help" -> {
                    MessageEmbed messageEmbed = new EmbedBuilder().setTitle("Help commands")
                            .addField("?share <plataforma> <login> <senha> <temAutenticador?True ou False>", "Registra a conta na base de dados.", false)
                            .addField("?buscar <Plataforma>", "Busca a conta da plataforma desejada", false)
                            .addField("?help", "Mostra os comandos do bot", false)
                            .setColor(Color.YELLOW)
                            .setThumbnail("https://cdn-icons-png.flaticon.com/512/1289/1289376.png")
                            .build();

                    messageReceivedEvent.getChannel().sendMessageEmbeds(messageEmbed).queue(message -> {
                        try {
                            TimeUnit.SECONDS.sleep(20);
                        }catch (InterruptedException e){
                            message.delete().queue();
                            messageReceivedEvent.getMessage().delete().queue();
                        }
                        message.delete().queue();
                        messageReceivedEvent.getMessage().delete().queue();
                    });

                }
            }
        } catch (RuntimeException exception) {
            MessageEmbed messageEmbed = new EmbedBuilder().setTitle("Erro!").setDescription(exception.getMessage()).build();

            messageReceivedEvent.getChannel().sendMessageEmbeds(messageEmbed).queue(message -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    message.delete().queue();
                    messageReceivedEvent.getMessage().delete().queue();
                }
                message.delete().queue();
                messageReceivedEvent.getMessage().delete().queue();
            });
        }
    }
}
