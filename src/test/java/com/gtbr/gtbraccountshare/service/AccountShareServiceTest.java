package com.gtbr.gtbraccountshare.service;

import com.gtbr.gtbraccountshare.exception.ObjectNotFoundException;
import com.gtbr.gtbraccountshare.model.AccountShare;
import com.gtbr.gtbraccountshare.repository.AccountShareRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountShareServiceTest {

    private AccountShareRepository accountShareRepository;

    private AccountShareService accountShareService;


    public AccountShareServiceTest() {
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

        AccountShare createdAccountShare = accountShareService.createAccountShare("teste", "teste", "teste", true, "teste");

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
    void findPlatform_not_found() {
        Mockito.when(accountShareRepository.findByPlatform("")).thenReturn(Optional.empty());

        String message = assertThrows(ObjectNotFoundException.class, () -> {
            accountShareService.findPlatform("");
        }).getMessage();
        assertEquals("Esta plataforma nao foi cadastrada ainda.", message);
    }

    @Test
    void findAll() {
        List<AccountShare> test = List.of(new AccountShare());
        Mockito.when(accountShareRepository.findAll()).thenReturn(test);
        List <AccountShare> accountShare = accountShareService.findAll();
        assertEquals(test.get(0).getId(),accountShare.get(0).getId());
    }

}