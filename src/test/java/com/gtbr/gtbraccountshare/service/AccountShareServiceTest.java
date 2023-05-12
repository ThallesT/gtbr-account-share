package com.gtbr.gtbraccountshare.service;

import com.gtbr.gtbraccountshare.exception.ObjectNotFoundException;
import com.gtbr.gtbraccountshare.model.AccountShare;
import com.gtbr.gtbraccountshare.model.Thumbnails;
import com.gtbr.gtbraccountshare.repository.AccountShareRepository;
import com.gtbr.gtbraccountshare.repository.ThumbnailsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountShareServiceTest {

    private AccountShareRepository accountShareRepository;

    private ThumbnailsRepository thumbnailsRepository;

    private ThumbnailsService thumbnailsService;

    private AccountShareService accountShareService;


    public AccountShareServiceTest() {
        this.accountShareRepository = Mockito.mock(AccountShareRepository.class);
        this.thumbnailsRepository = Mockito.mock(ThumbnailsRepository.class);
        this.accountShareService = new AccountShareService(this.accountShareRepository, this.thumbnailsRepository);
        this.thumbnailsService = new ThumbnailsService(this.thumbnailsRepository);
    }

    @Test
    void createAccountShare_success() {
        AccountShare accountShare = AccountShare.builder()
                .id(UUID.randomUUID())
                .username("username teste")
                .build();
        Mockito.when(accountShareRepository.save(Mockito.any())).thenReturn(accountShare);
        Thumbnails thumbnails = Thumbnails.builder()
                .platform("Plataforma teste")
                .build();
        Mockito.when(thumbnailsRepository.save(Mockito.any())).thenReturn(thumbnails);

        AccountShare createdAccountShare = accountShareService.createAccountShare("teste", "teste", "teste", true, "teste");

        Thumbnails createThumbnails = thumbnailsService.createThumbnail("teste","teste");

        assertEquals(accountShare.getId(), createdAccountShare.getId());
        assertEquals(thumbnails.getPlatform(), createThumbnails.getPlatform());
    }

    @Test
    void findPlatform_success() {
        String platform = "netflix";
        AccountShare teste = AccountShare.builder()
                .id(UUID.randomUUID())
                .platform(platform)
                .build();
        Mockito.when(accountShareRepository.findByPlatform(platform)).thenReturn(List.of(teste));

        AccountShare accountShare = accountShareService.findPlatform(platform);

        assertEquals(teste.getPlatform(), accountShare.getPlatform());
    }

    @Test
    void findPlatform_not_found() {
        Mockito.when(accountShareRepository.findByPlatform("")).thenReturn(List.of());

        String message = assertThrows(ObjectNotFoundException.class, () -> {
            accountShareService.findPlatform("");
        }).getMessage();
        assertEquals("Esta plataforma nao foi cadastrada ainda.", message);
    }

    @Test
    void findAll() {
        List<AccountShare> test = List.of(new AccountShare());
        Mockito.when(accountShareRepository.findAll()).thenReturn(test);
        List<AccountShare> accountShare = accountShareService.findAll();
        assertEquals(test.get(0).getId(), accountShare.get(0).getId());
    }

    @Test
    void deletePlatform_success() {
        List<AccountShare> accountShareList = List.of(AccountShare.builder().id(UUID.randomUUID()).build());
        Mockito.when(accountShareRepository.findByPlatformAndOwner("teste", "123")).thenReturn(accountShareList);

        assertDoesNotThrow(() -> accountShareService.deletePlatform("teste","123"));
    }

    @Test
    void deletePlatformNotFound(){
        assertThrows(ObjectNotFoundException.class, () -> accountShareService.deletePlatform("",""));
    }
}