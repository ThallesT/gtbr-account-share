package com.gtbr.gtbraccountshare.model;

import com.gtbr.gtbraccountshare.model.enums.AccountShareStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class AccountShare {

    @Id
    private UUID id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String owner;

    @Column
    private String platform;

    @Column
    private boolean authenticator;

    @Column
    private LocalDateTime createdAt;

    @Column
    @Enumerated(EnumType.STRING)
    private AccountShareStatus status;

}
