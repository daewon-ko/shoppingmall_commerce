package shoppingmall.web.api.message.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shoppingmall.domainrdb.message.service.MessageService;
import shoppingmall.web.api.message.dto.ChatMessageRequestDto;
import shoppingmall.web.api.message.dto.ChatMessageResponseDto;
import shoppingmall.web.common.annotataion.Usecase;

import java.util.UUID;

@RequiredArgsConstructor
@Usecase
public class MessageUsecase {

    private final MessageService messageService;

    public void saveMessage(final ChatMessageRequestDto messageDto, final String roomId) {
        String content = messageDto.getContent();
        Long senderId = messageDto.getSenderId();
        messageService.saveMessage(content, senderId, roomId);
    }


    public Page<ChatMessageResponseDto> getMessageByRoomId(final UUID roomId, final Pageable pageable) {
        return messageService.getMessageByRoomId(roomId, pageable).map(ChatMessageResponseDto::from);
    }


}
