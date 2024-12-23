package shoppingmall.core.domain.chat.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shoppingmall.core.domain.chat.entity.ChatRoom;
import shoppingmall.core.domain.chat.repository.ChatRoomRepository;
import shoppingmall.core.domain.product.entity.Product;
import shoppingmall.core.domain.product.repository.ProductRepository;
import shoppingmall.core.domain.user.entity.User;
import shoppingmall.core.domain.user.entity.UserRole;
import shoppingmall.core.domain.user.repository.UserRepository;
import shoppingmall.core.support.RepositoryTestSupport;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ChatRoomRepositoryTest extends RepositoryTestSupport {
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

