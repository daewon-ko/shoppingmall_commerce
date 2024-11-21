package shppingmall.commerce.message.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shppingmall.commerce.message.dto.ChatMessageResponseDto;
import shppingmall.commerce.message.service.MessageService;
import shppingmall.commerce.global.ApiResponse;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MessageController {

    private final MessageService messageService;
    @GetMapping("/chat/chatRoom/{roomId}/messages")
    public ApiResponse<Page<ChatMessageResponseDto>> chatMessages(@PathVariable(name = "roomId") UUID roomId, Pageable pageable) {
        return ApiResponse.ok(messageService.getMessageByRoomId(roomId, pageable));
    }
}
