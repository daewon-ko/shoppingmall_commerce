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
}
