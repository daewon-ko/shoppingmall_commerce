package shppingmall.commerce.user.entity;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * User에 관한 구체적인 속성 등은 문제의 요구사항에서 다루는 바가 아니므로
 * 간단하게 정의한다.
 */
@Entity
@Table
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


}
