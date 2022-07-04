package com.gtbr.gtbraccountshare.service;

import com.gtbr.gtbraccountshare.model.AccountShare;
import com.gtbr.gtbraccountshare.model.Request;
import com.gtbr.gtbraccountshare.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    public void creteRequest(AccountShare accountShare, String channelId, String channelName, String userDiscordId, String userDiscordTag) {
        requestRepository.save(Request.builder()
                .id(UUID.randomUUID())
                .accountShare(accountShare)
                .channelId(channelId)
                .channelName(channelName)
                .userDiscordId(userDiscordId)
                .userDiscordTag(userDiscordTag)
                .requestedAt(LocalDateTime.now())
                .build());
    }

}
