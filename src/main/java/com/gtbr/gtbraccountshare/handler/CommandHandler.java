package com.gtbr.gtbraccountshare.handler;

import com.gtbr.gtbraccountshare.model.AccountShare;
import com.gtbr.gtbraccountshare.service.AccountShareService;
import com.gtbr.gtbraccountshare.service.RequestService;
import com.gtbr.gtbraccountshare.service.ThumbnailsService;
import com.gtbr.gtbraccountshare.utils.MessageUtils;
import com.gtbr.gtbraccountshare.utils.SleepUtils;
import com.gtbr.gtbraccountshare.utils.SpringUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

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
                        SleepUtils.sleep(1, message, messageReceivedEvent);
                    });
                }
                case "buscar" -> {
                    String platform = fullMessage.split(" ")[1];
                    AccountShare accountShare = accountShareService.findPlatform(platform);
                    requestService.createRequest(accountShare,
                            messageReceivedEvent.getChannel().getId(),
                            messageReceivedEvent.getChannel().getName(),
                            messageReceivedEvent.getAuthor().getId(),
                            messageReceivedEvent.getAuthor().getAsTag());


                    MessageEmbed messageEmbed = new EmbedBuilder()
                            .setTitle(accountShare.getPlatform())
                            .setDescription("Esta mensagem expira em 20 segundos.")
                            .setFooter("id: " + accountShare.getId())
                            .addField("Login", accountShare.getUsername(), true)
                            .addField("Password", accountShare.getPassword(), true)
                            .addBlankField(false)
                            .addField("Tem autenticador / 2FA", accountShare.isAuthenticator() ? "Sim" : "Nao", true)
                            .addField("Dono da conta", "", true)
                            .setColor(Color.BLUE)
                            .setThumbnail(thumbnailsService.findThumbnail(accountShare.getPlatform()).getImageUrl())
                            .build();

                    if (!messageReceivedEvent.getChannel().getId().equals("993348110652805182"))
                        messageReceivedEvent
                                .getChannel()
                                .sendMessage("As credenciais foram enviadas no chat " + jda.getTextChannelById(993348110652805182L).getAsMention() + ", corra pois ela vai expirar em 20 segundos.")
                                .queue();

                    jda.getTextChannelById(993348110652805182L).sendMessageEmbeds(messageEmbed).queue(message -> {
                        SleepUtils.sleep(20, message, messageReceivedEvent);
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
                        SleepUtils.sleep(20, message, messageReceivedEvent);
                    });
                }
                default -> {
                    messageReceivedEvent.getChannel().sendMessage("Esse comando não foi reconhecido, para receber ajuda use o comando: ```?help```").queue(message -> {
                        SleepUtils.sleep(20, message, messageReceivedEvent);
                    });

                }

            }
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            MessageEmbed messageEmbed = new EmbedBuilder().setTitle("Erro!").setDescription(exception.getMessage()).build();

            messageReceivedEvent.getChannel().sendMessageEmbeds(messageEmbed).queue(message -> {
               SleepUtils.sleep(10, message, messageReceivedEvent);
            });
        }
    }
}
