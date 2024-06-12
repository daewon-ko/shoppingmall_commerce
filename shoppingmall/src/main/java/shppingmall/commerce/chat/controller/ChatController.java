package shppingmall.commerce.chat.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;
import shppingmall.commerce.chat.dto.ChatRoomCreateDto;
import shppingmall.commerce.chat.dto.ChatRoomResponseDto;
import shppingmall.commerce.chat.entity.ChatRoom;
import shppingmall.commerce.chat.service.ChatRoomService;

import java.net.URI;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatRoomService chatRoomService;

    @GetMapping()
    public String home() {
        return "index";
    }


    @PostMapping("/chat/chatRoom")
    public ResponseEntity<Void> createChatRoom(@RequestBody ChatRoomCreateDto chatRoomCreateDto, UriComponentsBuilder uriComponentsBuilder) {
        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.createRoom(chatRoomCreateDto);
        URI uri = uriComponentsBuilder.path("/chat/chatRoom/{id}").buildAndExpand(chatRoomResponseDto.getId()).toUri();
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(uri).build();
    }

    @GetMapping("/chat/chatRoom/{roomId}")
    public String chatRoom(@PathVariable(name = "roomId") UUID roomId, Model model) {

        ChatRoomResponseDto chatRoom = chatRoomService.getChatRoom(roomId);
        model.addAttribute("chatRoom", chatRoom);
        return "chatRoom";
    }

}
