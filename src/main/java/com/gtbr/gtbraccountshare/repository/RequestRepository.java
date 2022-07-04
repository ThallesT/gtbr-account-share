package com.gtbr.gtbraccountshare.repository;

import com.gtbr.gtbraccountshare.model.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RequestRepository extends CrudRepository<Request, UUID> {
}
