package com.gtbr.gtbraccountshare.service;

import com.gtbr.gtbraccountshare.model.AccountShare;
import com.gtbr.gtbraccountshare.repository.AccountShareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountShareService {

    private final AccountShareRepository accountShareRepository;

    public void createAccountShare(String username, String password, String owner, boolean authenticator, String platform) {
        AccountShare accountShare = AccountShare.builder()
                .id(UUID.randomUUID())
                .username(username)
                .password(password)
                .owner(owner)
                .authenticator(authenticator)
                .createdAt(LocalDateTime.now())
                .platform(platform)
                .build();

        accountShareRepository.save(accountShare);

    }

    public AccountShare findPlatform(String platform) {
        return accountShareRepository.findByPlatform(platform).orElseThrow(() -> {
            throw new RuntimeException("Esta plataforma nao foi cadastrada ainda.");
        });
    }
}
