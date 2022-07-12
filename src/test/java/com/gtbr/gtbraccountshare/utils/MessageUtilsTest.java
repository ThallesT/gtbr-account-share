package com.gtbr.gtbraccountshare.utils;

import com.gtbr.gtbraccountshare.model.AccountShare;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageUtilsTest {
    public MessageUtilsTest() {

    }

    @Test
    void getCommandFromMessage() {
        String command = MessageUtils.getCommandFromMessage("?buscar");
        assertEquals("buscar", command);
    }

    @Test
    void buildBuscarMessage() {
        MessageEmbed teste = MessageUtils.buildBuscarMessage(AccountShare.builder()
                .platform(" ")
                .username(" ")
                .password(" ")
                .authenticator(true)
                .build(), "https://google.com");

        assertEquals(" ", teste.getTitle());

    }

    @Test
    void buildHelpMessage() {
        MessageEmbed teste = MessageUtils.buildHelpMessage();

        assertEquals("Help commands", teste.getTitle());

    }
}