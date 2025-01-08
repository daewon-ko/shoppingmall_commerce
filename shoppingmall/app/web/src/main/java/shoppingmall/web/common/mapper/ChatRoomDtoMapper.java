package shoppingmall.web.common.mapper;

import shoppingmall.domainrdb.chat.ChatRoomDomain;
import shoppingmall.domainrdb.user.entity.UserRole;
import shoppingmall.web.api.chat.dto.ChatRoomCreateDto;
import shoppingmall.web.api.chat.dto.ChatRoomResponseDto;

public class ChatRoomDtoMapper {

    // Buyer가 채팅방에 입장시에 만들어주는 ResponseDTO이므로 UserRole은 항상 BUYER로 설정
    public static ChatRoomResponseDto toBuyerChatRoomResponseDto(final ChatRoomDomain chatRoomDomain) {
        return ChatRoomResponseDto.builder()
                .roomId(chatRoomDomain.getChatRoomId())
                .sellerId(chatRoomDomain.getSeller().getUserId())
                .buyerId(chatRoomDomain.getBuyer().getUserId())
                .userRole(UserRole.BUYER)
                .build();
    }
}
