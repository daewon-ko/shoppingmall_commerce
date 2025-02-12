package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.chat.ChatRoomDomain;
import shoppingmall.domainrdb.chat.entity.ChatRoom;

public class ChatRoomEntityMapper {
//    public static ChatRoom toEntity(ChatRoomDomain chatRoomDomain) {
//        return ChatRoom.builder()
//                UserEntityMapper.toEntity(chatRoomDomain.getBuyer())
//                .buyer(chatRoomDomain.getBuyer())
//                .seller(chatRoomDomain.getSeller())
//                .product(chatRoomDomain.getProduct())
//                .build();
//    }

    public static ChatRoomDomain toChatRoomDomain(ChatRoom chatRoom) {
        return ChatRoomDomain.createForRead(chatRoom);
    }
}
