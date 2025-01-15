package shoppingmall.domainrdb.chat;

import lombok.Getter;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainrdb.user.UserDomain;

import java.util.UUID;


@Getter
public class ChatRoomDomain {
    // ChatRoomEntity의 Id값이 UUID로 설정되어있으므로 UUID로 설정
    // API에서 ChatRoom Entity의 id필요시 Return해주기 위해 id값을 필드로 둔다.
    private final UUID chatRoomId;
    private final UserDomain seller;
    private final UserDomain buyer;
    private final ProductDomain productDomain;

    public ChatRoomDomain(UUID chatRoomId, UserDomain seller, UserDomain buyer, ProductDomain productDomain) {
        this.chatRoomId = chatRoomId;
        this.seller = seller;
        this.buyer = buyer;
        this.productDomain = productDomain;
    }
}
