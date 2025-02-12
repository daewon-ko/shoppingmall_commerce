package io.springbatch.domainrdb.chat.service;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import shoppingmall.domainrdb.domain.chat.dto.ChatRoomCreateDto;
import shoppingmall.domainrdb.domain.chat.dto.ChatRoomResponseDto;
import shoppingmall.domainrdb.domain.chat.repository.ChatRoomRepository;
import shoppingmall.domainrdb.domain.product.entity.Product;
import shoppingmall.domainrdb.domain.product.repository.ProductRepository;
import shoppingmall.domainrdb.domain.user.entity.User;
import shoppingmall.domainrdb.domain.user.entity.UserRole;
import shoppingmall.domainrdb.domain.user.repository.UserRepository;
import shoppingmall.domainrdb.support.IntegrationTestSupport;


class ChatRoomRdbServiceTest extends IntegrationTestSupport {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Mock
    private HttpSession httpSession;

    private User buyer;
    private User seller;
    private Product product;

    @BeforeEach
    void setUp() {
        buyer = createUser("userA", UserRole.BUYER);
        seller = createUser("userB", UserRole.SELLER);
        product = createProduct(10000, "상품A");

        buyer = userRepository.save(buyer);
        seller = userRepository.save(seller);
        product = productRepository.save(product);

    }

    @AfterEach
    void tearDown() {
        chatRoomRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

    @DisplayName("상품 id, 구매자 id, 판매자 id로 채팅방을 생성할 수 있다.")
    @Test
    void createChatRoomWithProductIdAndUsers() {
        //given


        ChatRoomCreateDto createRequest = ChatRoomCreateDto.builder()
                .productId(product.getId())
                .sellerId(seller.getId())
                .buyerId(buyer.getId())
                .build();

        //when
        ChatRoomResponseDto response = chatRoomService.createRoom(createRequest);

        //then
        assertThat(response)
                .extracting(ChatRoomResponseDto::getBuyerId, ChatRoomResponseDto::getSellerId)
                .containsExactly(buyer.getId(), seller.getId());
    }

    @DisplayName("채팅방을 생성 후, 채팅방ID, 사용자 ID, 판매자ID 등의 정보로 조회할 수 있다. ")
    @Test
    void getChatRoomById() {
        //given
        ChatRoomCreateDto createRequest = ChatRoomCreateDto.builder()
                .productId(product.getId())
                .sellerId(seller.getId())
                .buyerId(buyer.getId())
                .build();

        ChatRoomResponseDto createdRoom = chatRoomService.createRoom(createRequest);
        // 사용자가 접근했다고 가정
        Mockito.when(httpSession.getAttribute("user")).thenReturn(buyer);

        //when
        ChatRoomResponseDto getChatRoom = chatRoomService.getChatRoom(createdRoom.getRoomId(), httpSession);

        //then
        assertThat(getChatRoom).
                extracting(ChatRoomResponseDto::getRoomId,
                        ChatRoomResponseDto::getSellerId,
                        ChatRoomResponseDto::getBuyerId,
                        ChatRoomResponseDto::getSenderId)
                .containsExactly(createdRoom.getRoomId(), seller.getId(), buyer.getId(), buyer.getId());

    }



    private static User createUser(String name, UserRole userRole) {
        User seller = User.builder()
                .name(name)
                .userRole(userRole)
                .build();
        return seller;
    }

    private static Product createProduct(int price, String name) {
        Product product = Product.builder()
                .price(price)
                .name(name)
                .build();
        return product;
    }

}