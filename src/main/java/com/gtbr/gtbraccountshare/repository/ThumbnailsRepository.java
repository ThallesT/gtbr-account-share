package com.gtbr.gtbraccountshare.repository;

import com.gtbr.gtbraccountshare.model.Thumbnails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ThumbnailsRepository extends CrudRepository<Thumbnails, UUID> {

    @Query("SELECT t FROM Thumbnails t WHERE lower(t.platform) = lower(:platform) ")
    Optional<Thumbnails> findByPlatform(String platform);
}
