package shoppingmall.domainrdb.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shoppingmall.domainrdb.domain.chat.entity.ChatRoom;
import shoppingmall.domainrdb.domain.product.entity.Product;
import shoppingmall.domainrdb.domain.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.buyer = :buyer AND cr.seller = :seller AND cr.product = :product")
    Optional<ChatRoom> findByBuyerAndSellerAndProduct(@Param("buyer") User buyer, @Param("seller") User seller, @Param("product") Product product);
}
