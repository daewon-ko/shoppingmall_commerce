package shoppingmall.domainservice.domain.chatroom;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.chat.ChatRoomDomain;
import shoppingmall.domainrdb.chat.service.ChatRoomRdbService;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainservice.common.annotation.DomainService;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class ChatRoomSearchService {

    private final ChatRoomRdbService chatRoomRdbService;

    public ChatRoomDomain searchChatRoom(final UUID roomId) {
         return chatRoomRdbService.getChatRoom(roomId);
    }



}
