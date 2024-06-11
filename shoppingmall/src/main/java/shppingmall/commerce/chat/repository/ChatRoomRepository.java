package shppingmall.commerce.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.chat.entity.ChatRoom;

import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
}
