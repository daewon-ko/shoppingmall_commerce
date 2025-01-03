package shoppingmall.web.api.message.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shoppingmall.domainrdb.message.dto.ChatMessageRequestDto;
import shoppingmall.domainrdb.message.dto.ChatMessageResponseDto;
import shoppingmall.domainrdb.message.service.MessageService;
import shoppingmall.web.common.annotataion.Usecase;

import java.util.UUID;

@RequiredArgsConstructor
@Usecase
public class MessageUsecase {

    private final MessageService messageService;


    public Page<ChatMessageResponseDto> getMessageByRoomId(final UUID roomId, final Pageable pageable) {
        return messageService.getMessageByRoomId(roomId, pageable);
    }

    public ChatMessageResponseDto saveMessage(final ChatMessageRequestDto messageDto, final String roomId) {
        return messageService.saveMessage(messageDto, roomId);
    }


}
