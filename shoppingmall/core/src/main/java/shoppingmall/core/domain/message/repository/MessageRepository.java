package shoppingmall.core.domain.message.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.core.domain.chat.entity.ChatRoom;
import shoppingmall.core.domain.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom, Pageable pageable);
}
