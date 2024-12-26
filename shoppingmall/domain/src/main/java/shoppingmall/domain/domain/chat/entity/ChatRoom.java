package shoppingmall.domain.domain.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import shoppingmall.domain.common.BaseEntity;
import shoppingmall.domain.domain.product.entity.Product;
import shoppingmall.domain.domain.user.entity.User;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatRoom extends BaseEntity {
    /**
     * TODO : PK를 UUID와 같은 식으로 뒀을 때,
     *  조회 - 인덱스와 관련해서 이슈가 되는 부분을 고려해야하지 않을까?
     */
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

    @Builder
    private ChatRoom(User seller, User buyer, Product product) {
        this.seller = seller;
        this.buyer = buyer;
        this.product = product;
    }
}
