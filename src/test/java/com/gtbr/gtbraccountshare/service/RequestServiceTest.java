package com.gtbr.gtbraccountshare.service;

import com.gtbr.gtbraccountshare.model.AccountShare;
import com.gtbr.gtbraccountshare.model.Request;
import com.gtbr.gtbraccountshare.repository.RequestRepository;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestServiceTest {

   private RequestRepository requestRepository;
   private RequestService requestService;
   private MessageReceivedEvent messageReceivedEvent;

   public RequestServiceTest(){
       this.requestRepository = Mockito.mock(RequestRepository.class);
       this.requestService = new RequestService(requestRepository);
       this.messageReceivedEvent = Mockito.mock(MessageReceivedEvent.class);
   }

    @Test
    void createRequest_success() {
        Request request = Request.builder()
               .id(UUID.randomUUID())
               .build();

        when(requestRepository.save(Mockito.any())).thenReturn(request);
        when(messageReceivedEvent.getChannel()).thenReturn(new MessageChannel() {
            @Override
            public long getLatestMessageIdLong() {
                return 0;
            }

            @Override
            public boolean canTalk() {
                return false;
            }

            @NotNull
            @Override
            public String getName() {
                return "asd";
            }

            @NotNull
            @Override
            public ChannelType getType() {
                return null;
            }

            @NotNull
            @Override
            public JDA getJDA() {
                return null;
            }

            @NotNull
            @Override
            public RestAction<Void> delete() {
                return null;
            }

            @Override
            public long getIdLong() {
                return 0;
            }
        });
        when(messageReceivedEvent.getAuthor()).thenReturn(new User() {
            @NotNull
            @Override
            public String getName() {
                return "null";
            }

            @NotNull
            @Override
            public String getDiscriminator() {
                return "null";
            }

            @Nullable
            @Override
            public String getAvatarId() {
                return null;
            }

            @NotNull
            @Override
            public String getDefaultAvatarId() {
                return "null";
            }

            @NotNull
            @Override
            public RestAction<Profile> retrieveProfile() {
                return mock(RestAction.class);
            }

            @NotNull
            @Override
            public String getAsTag() {
                return "null";
            }

            @Override
            public boolean hasPrivateChannel() {
                return false;
            }

            @NotNull
            @Override
            public RestAction<PrivateChannel> openPrivateChannel() {
                return mock(RestAction.class);
            }

            @NotNull
            @Override
            public List<Guild> getMutualGuilds() {
                return List.of();
            }

            @Override
            public boolean isBot() {
                return false;
            }

            @Override
            public boolean isSystem() {
                return false;
            }

            @NotNull
            @Override
            public JDA getJDA() {
                return mock(JDA.class);
            }

            @NotNull
            @Override
            public EnumSet<UserFlag> getFlags() {
                return EnumSet.of(null);
            }

            @Override
            public int getFlagsRaw() {
                return 0;
            }

            @NotNull
            @Override
            public String getAsMention() {
                return "null";
            }

            @Override
            public long getIdLong() {
                return 0;
            }
        });
        Request createdRequest = requestService.createRequest(new AccountShare(),messageReceivedEvent);
        assertEquals(request.getId(), createdRequest.getId());
    }
}