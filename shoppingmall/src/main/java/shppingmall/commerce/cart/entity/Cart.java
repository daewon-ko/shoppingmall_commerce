package shppingmall.commerce.cart.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import shppingmall.commerce.common.BaseEntity;
import shppingmall.commerce.user.entity.User;

@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", unique = true)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Cart(User user) {
        this.user = user;
    }

    @Builder
    protected Cart() {

    }


}

