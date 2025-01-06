package shoppingmall.web.api.chat.usecase;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.chat.entity.ChatRoom;
import shoppingmall.domainservice.domain.chatroom.ChatRoomMakeService;
import shoppingmall.domainservice.domain.chatroom.ChatRoomSearchService;
import shoppingmall.web.api.chat.dto.ChatRoomCreateDto;
import shoppingmall.web.api.chat.dto.ChatRoomResponseDto;
import shoppingmall.web.common.annotataion.Usecase;

import java.util.UUID;

@RequiredArgsConstructor
@Usecase
public class ChatRoomUsecase {

    private final ChatRoomMakeService chatRoomMakeService;
    private final ChatRoomSearchService chatRoomSearchService;


    public UUID createChatRoom(final ChatRoomCreateDto chatRoomCreateDto) {
        return chatRoomMakeService.makeChatRoom(chatRoomCreateDto.getSellerId(), chatRoomCreateDto.getBuyerId(), chatRoomCreateDto.getProductId());
    }

    // TODO : ChatRoomResponseDTO를 Usecase(Application Layer)에서 만들어줄지 또는 DomainService에서 만들어줄지 고민 필요

    public ChatRoomResponseDto searchChatRoom(final UUID roomId) {
        ChatRoom chatRoom = chatRoomSearchService.searchChatRoom(roomId);

        return ChatRoomResponseDto.builder()
                .roomId(chatRoom.getId())
                .sellerId(chatRoom.getSeller().getId())
                .buyerId(chatRoom.getBuyer().getId())
                .senderId(chatRoom.getBuyer().getId())
                .build();
    }





}
