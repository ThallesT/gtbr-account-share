package com.gtbr.gtbraccountshare.service;

import com.gtbr.gtbraccountshare.exception.ObjectNotFoundException;
import com.gtbr.gtbraccountshare.model.AccountShare;
import com.gtbr.gtbraccountshare.repository.AccountShareRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountShareServiceTest {

    private AccountShareRepository accountShareRepository;

    private AccountShareService accountShareService;


    public AccountShareServiceTest(){
        this.accountShareRepository = Mockito.mock(AccountShareRepository.class);
        this.accountShareService = new AccountShareService(this.accountShareRepository);
    }

    @Test
    void createAccountShare_success() {
        AccountShare accountShare = AccountShare.builder()
                .id(UUID.randomUUID())
                .username("username teste")
                .build();
        Mockito.when(accountShareRepository.save(Mockito.any())).thenReturn(accountShare);


        AccountShare createdAccountShare = accountShareService.createAccountShare("teste","teste", "teste", true, "teste");

        assertEquals(accountShare.getId(), createdAccountShare.getId());
    }

    @Test
    void findPlatform_success() {
        String platform = "netflix";
        AccountShare teste = AccountShare.builder()
                .id(UUID.randomUUID())
                .platform(platform)
                .build();
        Mockito.when(accountShareRepository.findByPlatform(platform)).thenReturn(Optional.of(teste));

        AccountShare accountShare = accountShareService.findPlatform(platform);

        assertEquals(teste.getPlatform(), accountShare.getPlatform());

    }
    @Test
    void findPlatform_not_found(){
        Mockito.when(accountShareRepository.findByPlatform("")).thenReturn(Optional.empty());

        String message = assertThrows(ObjectNotFoundException.class,()-> {
            accountShareService.findPlatform("");
        }).getMessage();
        assertEquals("Esta plataforma nao foi cadastrada ainda.", message);
    }
}