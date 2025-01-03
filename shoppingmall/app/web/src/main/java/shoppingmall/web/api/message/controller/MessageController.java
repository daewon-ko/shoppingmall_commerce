package shoppingmall.web.api.message.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shoppingmall.common.ApiResponse;
import shoppingmall.domainrdb.message.dto.ChatMessageResponseDto;
import shoppingmall.web.api.message.usecase.MessageUsecase;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MessageController {

    private final MessageUsecase messageUsecase;

    @GetMapping("/chat/chatRoom/{roomId}/messages")
    public ApiResponse<Page<ChatMessageResponseDto>> chatMessages(@PathVariable(name = "roomId") UUID roomId, Pageable pageable) {
        return ApiResponse.ok(messageUsecase.getMessageByRoomId(roomId, pageable));
    }
}
