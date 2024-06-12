package shppingmall.commerce.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shppingmall.commerce.chat.entity.ChatRoom;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.buyer = :buyer AND cr.seller = :seller AND cr.product = :product")
    Optional<List<ChatRoom>> findByBuyerAndSellerAndProduct(@Param("buyer") User buyer, @Param("seller") User seller, @Param("product") Product product);
}
