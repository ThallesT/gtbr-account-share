package com.gtbr.gtbraccountshare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    private UUID id;

    @OneToOne
    @JoinColumn
    private AccountShare accountShare;

    @Column
    private String userDiscordId;

    @Column
    private String userDiscordTag;

    @Column
    private String channelId;

    @Column
    private String channelName;

    @Column
    private LocalDateTime requestedAt;
}
