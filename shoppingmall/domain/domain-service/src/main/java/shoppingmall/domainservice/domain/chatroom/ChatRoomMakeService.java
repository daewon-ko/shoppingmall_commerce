package shoppingmall.domainservice.domain.chatroom;

import lombok.RequiredArgsConstructor;
import shoppingmall.domainrdb.chat.ChatRoomDomain;
import shoppingmall.domainrdb.chat.service.ChatRoomRdbService;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.product.domain.ProductDomain;
import shoppingmall.domainrdb.product.service.ProductRdbService;
import shoppingmall.domainrdb.user.UserDomain;
import shoppingmall.domainrdb.user.entity.UserRole;
import shoppingmall.domainrdb.user.service.UserRdbService;

import java.util.UUID;

@DomainRdbService
@RequiredArgsConstructor
public class ChatRoomMakeService {
    private final ChatRoomRdbService chatRoomRdbService;
    private final ProductRdbService productRdbService;
    private final UserRdbService userRdbService;


    public UUID makeChatRoom(final Long productId, final Long sellerId, final Long buyerId) {

        ProductDomain productDomain = productRdbService.getProductDomainByProductId(productId);

        UserDomain seller = userRdbService.findUserByIdAndUserRole(sellerId, UserRole.SELLER);
        UserDomain buyer = userRdbService.findUserByIdAndUserRole(buyerId, UserRole.BUYER);

        return chatRoomRdbService.createRoom(new ChatRoomDomain(null, seller, buyer, productDomain));
    }

}
