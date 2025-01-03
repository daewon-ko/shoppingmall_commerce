package shoppingmall.domainservice.domain.chatroom;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.chat.entity.ChatRoom;
import shoppingmall.domainrdb.chat.service.ChatRoomService;
import shoppingmall.domainrdb.common.annotation.DomainService;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class ChatRoomSearchService {

    private final ChatRoomService chatRoomService;

    public ChatRoom searchChatRoom(final UUID roomId) {
         return chatRoomService.getChatRoom(roomId);
    }



}
