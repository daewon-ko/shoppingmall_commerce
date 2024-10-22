package shppingmall.commerce.chat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.chat.entity.ChatRoom;
import shppingmall.commerce.chat.entity.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom, Pageable pageable);
}
