package com.gtbr.gtbraccountshare.service;

import com.gtbr.gtbraccountshare.model.AccountShare;
import com.gtbr.gtbraccountshare.model.Request;
import com.gtbr.gtbraccountshare.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    public void createRequest(AccountShare accountShare, MessageReceivedEvent messageReceivedEvent) {
        requestRepository.save(Request.builder()
                .id(UUID.randomUUID())
                .accountShare(accountShare)
                .channelId(messageReceivedEvent.getChannel().getId())
                .channelName(messageReceivedEvent.getChannel().getName())
                .userDiscordId(messageReceivedEvent.getAuthor().getId())
                .userDiscordTag(messageReceivedEvent.getAuthor().getAsTag())
                .requestedAt(LocalDateTime.now())
                .build());
    }

}
