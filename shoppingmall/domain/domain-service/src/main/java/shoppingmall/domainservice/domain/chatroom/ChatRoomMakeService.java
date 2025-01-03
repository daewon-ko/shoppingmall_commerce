package shoppingmall.domainservice.domain.chatroom;

import lombok.RequiredArgsConstructor;
import shoppingmall.common.annotation.DomainService;
import shoppingmall.domainrdb.chat.service.ChatRoomService;

import java.util.UUID;

@DomainService
@RequiredArgsConstructor
public class ChatRoomMakeService {
    private final ChatRoomService chatRoomService;


    public UUID makeChatRoom(final Long sellerId, final Long buyerId, final Long productId) {
        return chatRoomService.createRoom(sellerId, buyerId, productId);
    }

}
