package shppingmall.commerce.chat.controller;

import jakarta.servlet.http.HttpSession;
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
import shppingmall.commerce.chat.service.ChatRoomService;

import java.net.URI;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatRoomService chatRoomService;




    @PostMapping("/chat/chatRoom")
    public ResponseEntity<Void> createChatRoom(@RequestBody ChatRoomCreateDto chatRoomCreateDto, UriComponentsBuilder uriComponentsBuilder) {
        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.createRoom(chatRoomCreateDto);
        URI uri = uriComponentsBuilder.path("/chat/chatRoom/{id}").buildAndExpand(chatRoomResponseDto.getRoomId()).toUri();
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(uri).build();
    }

    @GetMapping("/chat/chatRoom/{roomId}")
    public String chatRoom(@PathVariable(name = "roomId") UUID roomId, Model model, HttpSession httpSession) {

        ChatRoomResponseDto chatRoom = chatRoomService.getChatRoom(roomId, httpSession);
        model.addAttribute("chatRoom", chatRoom);
        return "chatRoom";
    }

}
