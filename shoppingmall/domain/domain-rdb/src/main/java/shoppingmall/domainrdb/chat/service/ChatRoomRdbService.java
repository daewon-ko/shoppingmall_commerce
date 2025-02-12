package shoppingmall.domainrdb.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.ChatErrorCode;
import shoppingmall.domainrdb.chat.ChatRoomDomain;
import shoppingmall.domainrdb.chat.entity.ChatRoom;
import shoppingmall.domainrdb.chat.repository.ChatRoomRepository;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.mapper.ChatRoomEntityMapper;
import shoppingmall.domainrdb.product.repository.ProductRepository;
import shoppingmall.domainrdb.user.repository.UserRepository;


import java.util.Optional;
import java.util.UUID;

@DomainRdbService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomRdbService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    // TODO : 예외처리 커스텀 필요 및 세부적으로 작성 필요
    @Transactional
    public UUID createRoom(final ChatRoomDomain chatRoomDomain) {

//        ChatRoom chatRoom = ChatRoomEntityMapper.toEntity(chatRoomDomain);

        // JOIN으로 푼다.
        // 1. Buyer, Seller, Product를 참조하는 ChatRoom 객체를 생성한다.

        ChatRoom chatRoom = ChatRoom.builder()
                .seller(userRepository.getReferenceById(chatRoomDomain.getSellerId().getValue()))
                .buyer(userRepository.getReferenceById(chatRoomDomain.getBuyerId().getValue()))
                .product(productRepository.getReferenceById(chatRoomDomain.getProductId().getValue()))
                .build();


        // 아래와 같이 로직을 작성하면 Optional 안에 null이 아닌 빈 객체를 담고 있을 수도 있지 않을까?
        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByBuyerAndSellerAndProduct(chatRoom.getBuyer(), chatRoom.getSeller(), chatRoom.getProduct());

        // TODO: 로직 변경 필요.
        if (existingChatRoom.isPresent()) {  // existingChatRoom이 null이 아니라면
            ChatRoom findChatRoom = existingChatRoom.get();

            return findChatRoom.getId();
        }

        chatRoom = chatRoomRepository.save(chatRoom);

        return chatRoom.getId();
    }


    public ChatRoomDomain getChatRoom(UUID roomId) {


        ChatRoom findChatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new ApiException(ChatErrorCode.NO_EXIST_CHATROOM)
        );
        return ChatRoomEntityMapper.toChatRoomDomain(findChatRoom);
//
//        UUID chatRoomId = chatRoom.getId();
//        chatRoom.getBuyer();
//
//        // ChatRoom 객체로 UserRole을 조회하는 메서드 정의 필요
////        List<User> users = userRepository.findUserByChatRoom(chatRoomId).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 업슶니다."));
//
//        /**n
//         * seller냐 buyer냐에 따라서 분기가 필요하지 않을까?n
//         * 1. 똑같은 URL(/chat/chatRoom/{roomId}를 통n해 입장한다.
//         * 2. roomId로 채팅방을 조회한다.
//         * 3. 채팅방 객체에는 User(Seller), User(Buyer)가 저장되어 있다.
//         * 4. 해당 API에 접근한 주체가 누구인지는 어떻게 파악하나?
//         * - 인증의 문제n
//         */
//        // TODO : 세션방식 로그인을 이용. 그러나, 채팅방 조회하는 방법을 변경할 필요가 있음
//        // 현재는 구매자가 채팅방 조회시와 같은 플로우를 가정함
//        // 그러나 Seller의 경우에는?
//        User user = (User) httpSession.getAttribute("user");
//        UserRole userRole = user.getUserRole();
//        Long senderId = user.getId();
//
//
//        /**
//         * roomId, buyerId, sellerId,
//         */
//        return ChatRoomResponseDto.builder()
//                .roomId(chatRoom.getId())
//                .buyerId(chatRoom.getBuyer().getId())
//                .sellerId(chatRoom.getSeller().getId())
//                .userRole(userRole)
//                .senderId(senderId)
//                .build();
    }

}
