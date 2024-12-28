package shoppingmall.web.presentation.chat;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import shoppingmall.domain.domain.chat.dto.ChatRoomCreateDto;
import shoppingmall.domain.domain.chat.dto.ChatRoomResponseDto;
import shoppingmall.domain.domain.chat.service.ChatRoomService;

import java.net.URI;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class ChatController {
    private final ChatRoomService chatRoomService;


    /**
     * @param chatRoomCreateDto    - 추후 삭제 예정(미필요)
     *                             현재는 화면에서 채팅기능 구현확인했음을 위해 아래와 같이 작성했으나,
     *                             /products/{productId}/chat/chatRoom/{chatRoomID}와 같이 작성할 예정
     *                             <p>
     *                             ChatRoomCreateDTO 자체가 의미가 있나 싶은 생각이 듬.
     *                             따라서 해당 DTO는 삭제 예정
     * @param uriComponentsBuilder
     * @return
     */

    @PostMapping("/chat/chatRoom")
    public ResponseEntity<Void> createChatRoom(@RequestBody @Valid ChatRoomCreateDto chatRoomCreateDto, UriComponentsBuilder uriComponentsBuilder) {
        log.info("ChatRoomCreateRequestDto, {}", chatRoomCreateDto);
        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.createRoom(chatRoomCreateDto);
        URI uri = uriComponentsBuilder.path("/chat/chatRoom/{id}").buildAndExpand(chatRoomResponseDto.getRoomId()).toUri();
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(uri).build();
    }

    @GetMapping("/chat/chatRoom/{roomId}")
    public String chatRoom(@PathVariable(name = "roomId") UUID roomId, Model model, HttpSession httpSession) {

        ChatRoomResponseDto chatRoom = chatRoomService.getChatRoom(roomId, httpSession);
        model.addAttribute("chatRoom", chatRoom);
        log.info("ChatRoomResponseDto, {}", chatRoom);
        return "chatRoom";
    }


}
