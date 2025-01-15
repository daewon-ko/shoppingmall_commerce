package shoppingmall.domainservice.domain.chatroom;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.chat.ChatRoomDomain;
import shoppingmall.domainrdb.chat.service.ChatRoomRdbService;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;

import java.util.UUID;

@DomainRdbService
@RequiredArgsConstructor
public class ChatRoomSearchService {

    private final ChatRoomRdbService chatRoomRdbService;

    public ChatRoomDomain searchChatRoom(final UUID roomId) {
         return chatRoomRdbService.getChatRoom(roomId);
    }



}
