package shppingmall.commerce.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.chat.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
