package shppingmall.commerce.chat.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shppingmall.commerce.chat.entity.ChatRoom;
import shppingmall.commerce.message.repository.ChatRoomRepository;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // DB 교체
class ChatRoomRepositoryTest {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("채팅방을 생성한 후, 생성 시 조건이 되는 상품번호, 판매자 id, 구매자 id로 조회 시 재입장할 수 있다.")
    @Test
    void findChatRoomByProductAndSellerAndBuyer() {
        //given
        Product product = createProduct(10000, "상품A");
        productRepository.save(product);

        User seller = createUser("userA", UserRole.SELLER);
        User buyer = createUser("userB", UserRole.BUYER);

        userRepository.save(seller);
        userRepository.save(buyer);

        ChatRoom chatRoom = ChatRoom.
                builder()
                .buyer(buyer)
                .seller(seller)
                .product(product)
                .build();

        chatRoomRepository.save(chatRoom);

        //when
        Optional<ChatRoom> findChatRoom = chatRoomRepository.findByBuyerAndSellerAndProduct(buyer, seller, product);

        //then
        assertThat(findChatRoom)
                .isPresent()
                .hasValue(chatRoom)
                .hasValueSatisfying(
                        room -> {
                            assertThat(room.getBuyer()).isEqualTo(buyer);
                            assertThat(chatRoom.getSeller()).isEqualTo(seller);
                            assertThat(chatRoom.getProduct()).isEqualTo(product);
                        });

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

