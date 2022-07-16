package com.gtbr.gtbraccountshare.service;

import com.gtbr.gtbraccountshare.exception.ObjectNotFoundException;
import com.gtbr.gtbraccountshare.model.AccountShare;
import com.gtbr.gtbraccountshare.model.enums.AccountShareStatus;
import com.gtbr.gtbraccountshare.repository.AccountShareRepository;
import com.gtbr.gtbraccountshare.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountShareService {

    private final AccountShareRepository accountShareRepository;

    public AccountShare createAccountShare(String username, String password, String owner, boolean authenticator, String platform) {
        AccountShare accountShare = AccountShare.builder()
                .id(UUID.randomUUID())
                .username(username)
                .password(password)
                .owner(owner)
                .authenticator(authenticator)
                .createdAt(LocalDateTime.now())
                .platform(platform)
                .build();
        return accountShareRepository.save(accountShare);
    }

    public AccountShare findPlatform(String platform) {
        List<AccountShare> accountShareList = accountShareRepository.findByPlatform(platform);

        if (accountShareList.isEmpty()){
            throw new ObjectNotFoundException(Constants.ERROR_MESSAGE);
        }

        return accountShareList.get(0);
    }

    public List<AccountShare> findAll() {
        return accountShareRepository.findAll();

    }

    public void deletePlatform(String platform, String ownerId){
        List<AccountShare> accountShareList = accountShareRepository.findByPlatformAndOwner(platform,ownerId);

        if (accountShareList.isEmpty())
            throw new ObjectNotFoundException("Você não é o dono da plataforma para deleta-la");
        else{
            AccountShare accountShare = accountShareList.get(0);
            accountShare.setStatus(AccountShareStatus.UNAVAILABLE);
            accountShareRepository.save(accountShare);
        }
    }
}
