package shoppingmall.domainrdb.mapper;

import shoppingmall.domainrdb.chat.ChatRoomDomain;
import shoppingmall.domainrdb.chat.entity.ChatRoom;

public class ChatRoomEntityMapper {
    public static ChatRoom toEntity(ChatRoomDomain chatRoomDomain) {
        return ChatRoom.builder()
                .product(ProductEntityMapper.toProductEntity(chatRoomDomain.getProductDomain()))
                .buyer(UserEntityMapper.toUserEntity(chatRoomDomain.getBuyer()))
                .seller(UserEntityMapper.toUserEntity(chatRoomDomain.getSeller()))
                .build();
    }

    public static ChatRoomDomain toChatRoomDomain(ChatRoom chatRoom) {
        return new ChatRoomDomain(
                chatRoom.getId(),
                UserEntityMapper.toUserDomain(chatRoom.getSeller()),
                UserEntityMapper.toUserDomain(chatRoom.getBuyer()),
                ProductEntityMapper.toProductDomain(chatRoom.getProduct())
        );
    }
}
