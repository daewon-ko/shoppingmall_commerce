package shppingmall.commerce.chat.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shppingmall.commerce.chat.entity.ChatRoom;
import shppingmall.commerce.message.entity.Message;
import shppingmall.commerce.message.repository.ChatRoomRepository;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.support.RepositoryTestSupport;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static shppingmall.commerce.support.TestFixture.*;

class MessageRepositoryTest extends RepositoryTestSupport {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;


    @AfterEach
    void tearDown() {
        messageRepository.deleteAllInBatch();
        chatRoomRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();

    }

    @DisplayName("메시지를 저장 후, 조회하면 역순으로 조회된다.")
    @Test
    void findMessagesByRoomIdInDescOrder() {
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
        Page<Message> findMessages = messageRepository.findByChatRoomOrderByCreatedAtDesc(chatRoom, pageRequest);
        List<String> messageContents = findMessages.stream().map(m -> m.getContent()).collect(Collectors.toList());

        //then
        assertThat(messageContents).contains("firstMessage", "secondMessage", "thirdMessage");

    }


    @DisplayName("메시지를 저장후, 페이지에 메시지가 꽉차지 않는 경우를 검증한다.")
    @Test
    void findMessageWhenPageIsNotFull() {
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

        Pageable pageRequest1 = PageRequest.of(0, 2);
        Pageable pageRequest2 = PageRequest.of(1, 2);
        //when

        Page<Message> page1 = messageRepository.findByChatRoomOrderByCreatedAtDesc(chatRoom, pageRequest1);
        Page<Message> page2 = messageRepository.findByChatRoomOrderByCreatedAtDesc(chatRoom, pageRequest2);

        //then
        assertThat(page1.getContent().get(0).getContent()).isEqualTo("thirdMessage");
        assertThat(page1.getContent().get(1).getContent()).isEqualTo("secondMessage");
        assertThat(page2.getContent().get(0).getContent()).isEqualTo("firstMessage");

    }


}