package com.gtbr.gtbraccountshare.service;

import com.gtbr.gtbraccountshare.model.Thumbnails;
import com.gtbr.gtbraccountshare.repository.ThumbnailsRepository;
import com.gtbr.gtbraccountshare.utils.SpringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ThumbnailsServiceTest {
    //definir e instanciar a classe
    private ThumbnailsService thumbnailsService;
    //mockar seus metodos
    private ThumbnailsRepository thumbnailsRepository;

    public ThumbnailsServiceTest() {
        this.thumbnailsRepository = Mockito.mock(ThumbnailsRepository.class);
        this.thumbnailsService = new ThumbnailsService(thumbnailsRepository);
    }

    @Test
    void findThumbnail_success() {
        Thumbnails thumbnails = new Thumbnails();
        thumbnails.setImageUrl("teste");

        when(thumbnailsRepository.findByPlatform("teste")).thenReturn(Optional.of(thumbnails));
        //chamar o metodo
        Thumbnails thumbnailsFromService = thumbnailsService.findThumbnail("teste");

        //verificar
        assertEquals(thumbnails.getImageUrl(), thumbnailsFromService.getImageUrl());

    }

    @Test
    void findThumbnail_notFound() {
        Thumbnails thumbnailsNotFound = thumbnailsService.findThumbnail("teste");
        assertEquals("https://www.oversodoinverso.com.br/wp-content/uploads/2020/08/template.png", thumbnailsNotFound.getImageUrl());
    }
}