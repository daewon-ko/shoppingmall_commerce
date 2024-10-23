package shppingmall.commerce.message.entity;

import jakarta.persistence.*;
import lombok.*;
import shppingmall.commerce.chat.entity.ChatRoom;
import shppingmall.commerce.message.dto.ChatMessageResponseDto;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.user.entity.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User user;


    @Column(name = "message_content")
    private String content;

    @Builder
    private Message(ChatRoom chatRoom, User user, String content) {
        this.chatRoom = chatRoom;
        this.user = user;
        this.content = content;
    }

    public ChatMessageResponseDto of() {
        return ChatMessageResponseDto.builder()
                .id(id)
                .content(content)
                .senderId(user.getId())
                .chatRoomId(chatRoom.getId())
                .build();
    }
}
