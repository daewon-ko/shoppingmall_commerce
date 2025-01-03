package shoppingmall.web.api.chat.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import shoppingmall.domainrdb.domain.chat.dto.ChatRoomCreateDto;
import shoppingmall.domainrdb.domain.chat.dto.ChatRoomResponseDto;
import shoppingmall.domainrdb.domain.chat.service.ChatRoomService;
import shoppingmall.web.api.chat.usecase.ChatRoomUsecase;

import java.net.URI;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class ChatRoomController {

    private final ChatRoomUsecase chatRoomUsecase;


    /**
     * @param chatRoomCreateDto    - 추후 삭제 예정(미필요)
     *                             현재는 화면에서 채팅기능 구현확인했음을 위해 아래와 같이 작성했으나,
     *                             /products/{productId}/chat/chatRoom/{chatRoomID}와 같이 작성할 예정
     *                             <p>
     *                             ChatRoomCreateDTO 자체가 의미가 있나 싶은 생각이 듬.
     *                             따라서 해당 DTO는 삭제 예정
     *                             <p>
     *                             구매자가 채팅방을 조회하는 API
     * @param uriComponentsBuilder
     * @return
     */

    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/chat/chatRoom")
    public ResponseEntity<Void> createChatRoom(@RequestBody @Valid ChatRoomCreateDto chatRoomCreateDto, UriComponentsBuilder uriComponentsBuilder) {
        log.info("ChatRoomCreateRequestDto, {}", chatRoomCreateDto);
        URI uri = uriComponentsBuilder.path("/chat/chatRoom/{id}").buildAndExpand(chatRoomUsecase.createChatRoom(chatRoomCreateDto)).toUri();
        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(uri).build();
    }


    /**
     * '구매자'가 채팅방 조회하는 경우를 가정한 API
     *
     * @param roomId
     * @param model
     * @return
     */
    @PreAuthorize("hasRole('BUYER')")
    @GetMapping("/chat/chatRoom/{roomId}")
    public String chatRoom(@PathVariable(name = "roomId") UUID roomId, Model model) {

        ChatRoomResponseDto chatRoom = chatRoomUsecase.searchChatRoom(roomId);
        model.addAttribute("chatRoom", chatRoom);
        log.info("ChatRoomResponseDto, {}", chatRoom);
        return "chatRoom";
    }


    // TODO : 판매자가 채팅방 조회하는 API 구성 필요
    // @PreAuthorize와 같은 방법 이용해서 인증방법을 새로 구현해야할 것으로 보임


}
