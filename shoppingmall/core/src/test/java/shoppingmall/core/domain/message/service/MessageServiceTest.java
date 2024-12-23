package shoppingmall.core.domain.message.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shoppingmall.core.domain.chat.dto.ChatRoomCreateDto;
import shoppingmall.core.domain.chat.dto.ChatRoomResponseDto;
import shoppingmall.core.domain.chat.entity.ChatRoom;
import shoppingmall.core.domain.chat.repository.ChatRoomRepository;
import shoppingmall.core.domain.chat.service.ChatRoomService;
import shoppingmall.core.domain.message.dto.ChatMessageRequestDto;
import shoppingmall.core.domain.message.dto.ChatMessageResponseDto;
import shoppingmall.core.domain.message.entity.Message;
import shoppingmall.core.domain.message.entity.MessageType;
import shoppingmall.core.domain.message.repository.MessageRepository;
import shoppingmall.core.domain.message.service.MessageService;
import shoppingmall.core.domain.product.entity.Product;
import shoppingmall.core.domain.product.repository.ProductRepository;
import shoppingmall.core.domain.user.entity.User;
import shoppingmall.core.domain.user.entity.UserRole;
import shoppingmall.core.domain.user.repository.UserRepository;
import shoppingmall.core.support.IntegrationTestSupport;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static shoppingmall.core.support.TestFixture.*;


class MessageServiceTest extends IntegrationTestSupport {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;


    @AfterEach
    void tearDown() {
        messageRepository.deleteAllInBatch();
        chatRoomRepository.deleteAllInBatch();

    }

    // TODO : DynamicTest등과 같이 메시지를 여러 개 저장하는 등 시나리오 구성 필요

    @DisplayName("메시지 저장 시, 요청한 사용자의 정보와 내용이 정상적으로 저장된다.")
    @Test
    void saveMessage() {
        //given

        User buyer = createUser("userA","1234", UserRole.BUYER);
        User seller = createUser("userB", "1234",  UserRole.SELLER);
        Product product = createProduct(10000, "상품A");

        seller = userRepository.save(seller);
        buyer = userRepository.save(buyer);
        product = productRepository.save(product);

        ChatRoomCreateDto createRequest = createChatRoomRequest(product, seller, buyer);

        ChatRoomResponseDto response = chatRoomService.createRoom(createRequest);

        ChatMessageRequestDto chatMessageRequest = createChatMessageRequest(buyer, "안녕하세요 문의사항 있습니다.");


        //when
        ChatMessageResponseDto chatMessageResponseDto = messageService.saveMessage(chatMessageRequest, response.getRoomId().toString());

        //then
        assertThat(chatMessageResponseDto)
                .extracting(
                        ChatMessageResponseDto::getSenderId,
                        ChatMessageResponseDto::getContent)
                .contains(buyer.getId(), "안녕하세요 문의사항 있습니다.");

    }


    @DisplayName("저장된 메시지를 채팅방 Id를 기준으로 순서대로 조회할 수 있다.")
    @Test
    void findMessagesWithRoomId() {
        //given
        User buyer = createUser("buyer", "1234", UserRole.BUYER);
        User seller = createUser("seller", "1234", UserRole.SELLER);

        userRepository.saveAll(List.of(buyer, seller));

        Product product = createProduct(1000, "testProduct");
        productRepository.save(product);

        ChatRoom chatRoom = createChatRoom(seller, buyer, product);
        chatRoomRepository.save(chatRoom);

        Message message1 = createMessage(chatRoom, buyer, "firstMessage");
        Message message2 = createMessage(chatRoom, buyer, "secondMessage");
        Message message3 = createMessage(chatRoom, buyer, "thirdMessage");
        List<Message> messages = messageRepository.saveAll(List.of(message1, message2, message3));

        Pageable pageRequest = PageRequest.of(0, 10);

        //when
        List<ChatMessageResponseDto> content = messageService.getMessageByRoomId(chatRoom.getId(), pageRequest).getContent();

        //then
        assertThat(content).hasSize(3)
                .extracting(ChatMessageResponseDto::getContent)
                .containsExactly(
                        "thirdMessage",
                        "secondMessage",
                        "firstMessage"
                );

    }


    private static ChatMessageRequestDto createChatMessageRequest(User buyer, String content) {
        ChatMessageRequestDto chatMessageRequest = ChatMessageRequestDto.builder()
                .messageType(MessageType.ENTER)
                .senderId(buyer.getId())
                .content(content)
                .build();
        return chatMessageRequest;
    }

    private static ChatRoomCreateDto createChatRoomRequest(Product product, User seller, User buyer) {
        ChatRoomCreateDto createRequest = ChatRoomCreateDto.builder()
                .productId(product.getId())
                .sellerId(seller.getId())
                .buyerId(buyer.getId())
                .build();
        return createRequest;
    }



}