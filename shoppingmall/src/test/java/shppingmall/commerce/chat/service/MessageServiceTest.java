package shppingmall.commerce.chat.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shppingmall.commerce.chat.dto.ChatMessageRequestDto;
import shppingmall.commerce.chat.dto.ChatMessageResponseDto;
import shppingmall.commerce.chat.dto.ChatRoomCreateDto;
import shppingmall.commerce.chat.dto.ChatRoomResponseDto;
import shppingmall.commerce.chat.entity.MessageType;
import shppingmall.commerce.chat.repository.ChatRoomRepository;
import shppingmall.commerce.chat.repository.MessageRepository;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.support.IntegrationTestSupport;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;

import static org.assertj.core.api.Assertions.*;


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

        User buyer = createUser("userA", UserRole.BUYER);
        User seller = createUser("userB", UserRole.SELLER);
        Product product = createProduct(10000, "상품A");

        seller = userRepository.save(seller);
        buyer = userRepository.save(buyer);
        product = productRepository.save(product);

        ChatRoomCreateDto createRequest = createChatRoomRequest(product, seller, buyer);

        ChatRoomResponseDto response = chatRoomService.createRoom(createRequest);

        ChatMessageRequestDto chatMessageRequest = createChatMessageRequest(buyer);


        //when
        ChatMessageResponseDto chatMessageResponseDto = messageService.saveMessage(chatMessageRequest, response.getRoomId().toString());

        //then
        assertThat(chatMessageResponseDto)
                .extracting(
                        ChatMessageResponseDto::getSenderId,
                        ChatMessageResponseDto::getContent)
                .contains(buyer.getId(), "안녕하세요 문의사항 있습니다.");

    }

    private static ChatMessageRequestDto createChatMessageRequest(User buyer) {
        ChatMessageRequestDto chatMessageRequest = ChatMessageRequestDto.builder()
                .messageType(MessageType.ENTER)
                .senderId(buyer.getId())
                .content("안녕하세요 문의사항 있습니다.")
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

    private static User createUser(String name, UserRole userRole) {
        User user = User.builder()
                .name(name)
                .userRole(userRole)
                .build();
        return user;
    }

    private static Product createProduct(int price, String name) {
        Product product = Product.builder()
                .price(price)
                .name(name)
                .build();
        return product;
    }

}