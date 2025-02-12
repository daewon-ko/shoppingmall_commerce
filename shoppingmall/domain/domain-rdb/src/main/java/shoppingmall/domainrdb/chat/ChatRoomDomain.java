package shoppingmall.domainrdb.chat;

import lombok.Builder;
import lombok.Getter;
import shoppingmall.domainrdb.chat.entity.ChatRoom;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainrdb.product.domain.ProductId;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.UserId;

import java.util.UUID;


@Getter
public class ChatRoomDomain {
    // ChatRoomEntity의 Id값이 UUID로 설정되어있으므로 UUID로 설정
    // API에서 ChatRoom Entity의 id필요시 Return해주기 위해 id값을 필드로 둔다.
    private final UUID chatRoomId;
    private final UserId sellerId;
    private final UserId buyerId;
    private final ProductId productId;


    @Builder
    private ChatRoomDomain(UUID chatRoomId, UserId sellerId, UserId buyerId, ProductId productId) {
        this.chatRoomId = chatRoomId;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.productId = productId;
    }

    public static ChatRoomDomain createForRead(final ChatRoom chatRoom) {
        return ChatRoomDomain.builder()
                .chatRoomId(chatRoom.getId())
                .sellerId(UserId.from(chatRoom.getSeller().getId()))
                .buyerId(UserId.from(chatRoom.getBuyer().getId()))
                .productId(ProductId.from(chatRoom.getProduct().getId()))
                .build();
    }

    public static ChatRoomDomain createForWrite(final UserId sellerId, final UserId buyerId, final ProductId productId) {
        return ChatRoomDomain.builder()
                .sellerId(sellerId)
                .buyerId(buyerId)
                .productId(productId)
                .build();
    }
}
