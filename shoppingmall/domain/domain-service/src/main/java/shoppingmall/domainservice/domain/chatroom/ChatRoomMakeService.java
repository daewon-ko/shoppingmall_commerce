package shoppingmall.domainservice.domain.chatroom;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.chat.service.ChatRoomService;
import shoppingmall.domainrdb.common.annotation.DomainService;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class ChatRoomMakeService {
    private final ChatRoomService chatRoomService;


    public UUID makeChatRoom(final Long sellerId, final Long buyerId, final Long productId) {
        return chatRoomService.createRoom(sellerId, buyerId, productId);
    }

}
