package shoppingmall.domainrdb.cart.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.common.BaseEntity;
import shoppingmall.domainrdb.user.entity.User;

@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "cart")
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", unique = true)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Nullable
    private User user;

    @Builder
    private Cart(User user) {
        this.user = user;
    }

    @Builder
    protected Cart() {

    }


}

