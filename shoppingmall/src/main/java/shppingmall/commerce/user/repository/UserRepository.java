package shppingmall.commerce.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shppingmall.commerce.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
