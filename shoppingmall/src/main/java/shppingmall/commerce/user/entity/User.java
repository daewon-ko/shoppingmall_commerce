package shppingmall.commerce.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shppingmall.commerce.cart.entity.Cart;
import shppingmall.commerce.chat.entity.ChatRoom;

import java.util.List;
import java.util.Objects;

/**
 * User에 관한 구체적인 속성 등은 문제의 요구사항에서 다루는 바가 아니므로
 * 간단하게 정의한다.
 */
@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    @Builder
    private User(String name, String password, UserRole userRole) {
        this.name = name;
        this.password = password;
        this.userRole = userRole;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userRole);
    }

    //// TODO : UserRepository에서 ChatRoom과 조인해서 User를 조회하기위해 양방향 관계를 만들었으나, 다른방법은 없을까?
//    @OneToMany(mappedBy = "user")
//    private List<ChatRoom> chatRooms;



}
