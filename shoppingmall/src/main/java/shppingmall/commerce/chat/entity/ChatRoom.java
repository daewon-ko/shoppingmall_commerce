package shppingmall.commerce.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.user.entity.User;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class ChatRoom extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "chat_room_id")
    private UUID id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


}
