package shppingmall.commerce.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.user.entity.User;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column(name = "seller_id")
    @ManyToOne(fetch= FetchType.LAZY)
    private User seller;

    @Column(name = "buyer_id")
    @ManyToOne(fetch= FetchType.LAZY)
    private User buyer;



}
