package shppingmall.commerce.chat.controller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import shppingmall.commerce.ControllerTestSupport;
import shppingmall.commerce.chat.dto.ChatRoomCreateDto;
import shppingmall.commerce.chat.dto.ChatRoomResponseDto;
import shppingmall.commerce.global.exception.ApiException;
import shppingmall.commerce.global.exception.domain.ChatErrorCode;
import shppingmall.commerce.user.entity.UserRole;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ChatControllerTest extends ControllerTestSupport {


    @DisplayName("채팅방을 생성한후, 채팅방ID를 포함한 경로로 HTTP 302 요청을 보낸다.")
    @Test
    void createChatRoomThenSend302WithChatRoomId() throws Exception {
        //given

        UUID uuid = UUID.randomUUID();

        ChatRoomCreateDto chatRoomCreateRequest = ChatRoomCreateDto.builder()
                .productId(1L)
                .buyerId(1L)
                .sellerId(2L)
                .build();

        ChatRoomResponseDto chatRoomResponse = ChatRoomResponseDto.builder()
                .roomId(uuid)
                .sellerId(2L)
                .buyerId(1L)
                .senderId(1L)
                .build();

        // TODO : 아래와 같이 구체적 Request 작성시, chatRoomResponseDto는 null을 반환 이유는?
        /**
         * Mockito에서 프록시 객체를 생성해주기 때문인가? -> 계속해서 NPE가 발생한다. 이유를 잘 모르겠음.
         */

        Mockito.when(chatRoomService.createRoom(any(ChatRoomCreateDto.class))).thenReturn(chatRoomResponse);
//       Mockito.when(chatRoomService.createRoom((chatRoomCreateRequest))).thenReturn(chatRoomResponse);

        //when, then
        mockMvc.perform(post("/chat/chatRoom")
                        .content(objectMapper.writeValueAsString(chatRoomCreateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isSeeOther())
                .andExpect(header().string("Location", "http://localhost/chat/chatRoom/" + uuid));


    }

    @DisplayName("채팅방 생성시, 상품번호는 필수적이다.")
    @Test
    void createChatRoomWithOutProductId() throws Exception {
        //given

        UUID chatRoomId = UUID.randomUUID();


        ChatRoomCreateDto chatRoomCreateRequest = ChatRoomCreateDto.builder()
                .buyerId(1L)
                .sellerId(2L)
                .build();

        ChatRoomResponseDto chatRoomResponse = ChatRoomResponseDto.builder()
                .roomId(chatRoomId)
                .buyerId(1L)
                .sellerId(2L)
                .senderId(1L)
                .build();


        Mockito.when(chatRoomService.createRoom(any(ChatRoomCreateDto.class))).thenReturn(chatRoomResponse);

        //when, then
        mockMvc.perform(post("/chat/chatRoom")
                        .content(objectMapper.writeValueAsString(chatRoomCreateRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("상품번호를 반드시 입력해주세요."));

    }

    @DisplayName("채팅방을 조회할 수 있다.")
    @Test
    void getChatRoom() throws Exception {
        //given
        UUID chatRoomId = UUID.randomUUID();

        ChatRoomResponseDto response = ChatRoomResponseDto.builder()
                .roomId(chatRoomId)
                .userRole(UserRole.BUYER)
                .senderId(1L)
                .buyerId(2L)
                .sellerId(1L)
                .build();

        Mockito.when(chatRoomService.getChatRoom(any(UUID.class), any(HttpSession.class)))
                .thenReturn(response);


        //when, then
        mockMvc.perform(get("/chat/chatRoom/" + chatRoomId))
                .andExpect(status().isOk())
                .andExpect(view().name("chatRoom"))
                .andExpect(model().attributeExists("chatRoom"))
                .andExpect(model().attribute("chatRoom", response));


    }

    @DisplayName("채팅방 번호를 잘못 기입하면, 채팅방에 입장할 수 없다.")
    @Test
    void getChatRoomWithWrongRoomId() throws Exception {
        //given
        UUID chatRoomId = UUID.randomUUID();

        ChatRoomResponseDto response = ChatRoomResponseDto.builder()
                .roomId(chatRoomId)
                .userRole(UserRole.BUYER)
                .senderId(1L)
                .buyerId(2L)
                .sellerId(1L)
                .build();
        UUID wrongChatRoomId = UUID.randomUUID();

        Mockito.when(chatRoomService.getChatRoom(eq(wrongChatRoomId), any(HttpSession.class)))
                .thenThrow(new ApiException(ChatErrorCode.INVALID_CHATROOM_NUMBER));


        //when, then
        mockMvc.perform(get("/chat/chatRoom/" + wrongChatRoomId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 채팅방 번호입니다."));



    }


}