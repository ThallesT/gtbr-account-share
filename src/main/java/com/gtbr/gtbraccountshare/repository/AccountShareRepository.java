package com.gtbr.gtbraccountshare.repository;

import com.gtbr.gtbraccountshare.model.AccountShare;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountShareRepository extends CrudRepository<AccountShare, UUID> {

    @Query("SELECT ac FROM AccountShare ac WHERE ac.status = 'AVAILABLE' AND lower(ac.platform) = lower(:platform)")
    List<AccountShare> findByPlatform(String platform);

    @Query("SELECT ac FROM AccountShare ac WHERE ac.status = 'AVAILABLE' AND lower(ac.platform) = lower(:platform) AND ac.owner=:ownerID")
    List<AccountShare> findByPlatformAndOwner(String platform,String ownerID);

    @Query("SELECT ac FROM AccountShare ac WHERE ac.status = 'AVAILABLE'")
    List<AccountShare> findAll();
}
