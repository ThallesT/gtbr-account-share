package com.gtbr.gtbraccountshare.service;

import com.gtbr.gtbraccountshare.model.Thumbnails;
import com.gtbr.gtbraccountshare.repository.ThumbnailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class ThumbnailsService {

    private final ThumbnailsRepository thumbnailsRepository;

    public Thumbnails findThumbnail (String platform){

    return thumbnailsRepository.findByPlatform(platform).orElse(Thumbnails.builder()
            .imageUrl("https://www.oversodoinverso.com.br/wp-content/uploads/2020/08/template.png").build());
    }



}
