package shoppingmall.domainrdb.domain.message.entity;

import jakarta.persistence.*;
import lombok.*;
import shoppingmall.domainrdb.common.BaseEntity;
import shoppingmall.domainrdb.domain.chat.entity.ChatRoom;
import shoppingmall.domainrdb.domain.user.entity.User;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


}
