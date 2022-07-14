package com.gtbr.gtbraccountshare.repository;

import com.gtbr.gtbraccountshare.model.AccountShare;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountShareRepository extends CrudRepository<AccountShare, UUID> {

    @Query("SELECT ac FROM AccountShare ac WHERE lower(ac.platform) = lower(:platform)")
    Optional<AccountShare> findByPlatform(String platform);

    @Query("SELECT ac FROM AccountShare ac WHERE lower(ac.platform) = lower(:platform) AND ac.owner=:ownerID")
    Optional<AccountShare> findByPlatformAndOwner(String platform,String ownerID);
}
