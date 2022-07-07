package com.gtbr.gtbraccountshare.utils;

import com.gtbr.gtbraccountshare.model.AccountShare;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class MessageUtils {

    public static String getCommandFromMessage(String fullMessage) {
        var commandWithPrefix = fullMessage.split(" ")[0];
        var command = commandWithPrefix.replace("?", "");

        return command;
    }
    public static MessageEmbed buildBuscarMessage(AccountShare accountShare, String imageUrl){
        return new EmbedBuilder()
                .setTitle(accountShare.getPlatform())
                .setDescription("Esta mensagem expira em 20 segundos.")
                .setFooter("id: " + accountShare.getId())
                .addField("Login", accountShare.getUsername(), true)
                .addField("Password", accountShare.getPassword(), true)
                .addBlankField(false)
                .addField("Tem autenticador / 2FA", accountShare.isAuthenticator()
                        ? "Sim" : "Nao", true)
                .addField("Dono da conta", "", true)
                .setColor(Color.BLUE)
                .setThumbnail(imageUrl)
                .build();
    }
    public static MessageEmbed buildHelpMessage(){
        return new EmbedBuilder().setTitle("Help commands")
                .addField("?share <plataforma> <login> <senha> <temAutenticador?True ou False>",
                        "Registra a conta na base de dados.", false)
                .addField("?buscar <Plataforma>", "Busca a conta da plataforma desejada", false)
                .addField("?help", "Mostra os comandos do bot", false)
                .setColor(Color.YELLOW)
                .setThumbnail("https://cdn-icons-png.flaticon.com/512/1289/1289376.png")
                .build();
    }
}
