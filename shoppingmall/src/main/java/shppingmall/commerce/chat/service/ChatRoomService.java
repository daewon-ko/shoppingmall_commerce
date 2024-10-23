package shppingmall.commerce.chat.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shppingmall.commerce.chat.dto.ChatRoomCreateDto;
import shppingmall.commerce.chat.dto.ChatRoomResponseDto;
import shppingmall.commerce.chat.entity.ChatRoom;
import shppingmall.commerce.message.repository.ChatRoomRepository;
import shppingmall.commerce.global.exception.ApiException;
import shppingmall.commerce.global.exception.domain.ChatErrorCode;
import shppingmall.commerce.global.exception.domain.ProductErrorCode;
import shppingmall.commerce.global.exception.domain.UserErrorCode;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    // TODO : 예외처리 커스텀 필요 및 세부적으로 작성 필요
    public ChatRoomResponseDto createRoom(final ChatRoomCreateDto chatRoomCreateDto) {
        User buyer = userRepository.findById(chatRoomCreateDto.getBuyerId()).
                orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER));

        // buyer 검증
        if (!buyer.getUserRole().equals(UserRole.BUYER)) {
            throw new ApiException(UserErrorCode.INVALID_USER_INFORMATION);
        }
        User seller = userRepository.findById(chatRoomCreateDto.getSellerId()).
                orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER));
        // seller 검증
        if (!seller.getUserRole().equals(UserRole.SELLER)) {
            throw new ApiException(UserErrorCode.INVALID_USER_INFORMATION);
        }

        Product product = productRepository.findById(chatRoomCreateDto.getProductId())
                .orElseThrow(() -> new ApiException(ProductErrorCode.PRODUCT_NOT_FOUND));


        // 아래와 같이 로직을 작성하면 Optional 안에 null이 아닌 빈 객체를 담고 있을 수도 있지 않을까?
        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByBuyerAndSellerAndProduct(buyer, seller, product);


        // TODO: 로직 변경 필요.
        if (existingChatRoom.isPresent()) {  // existingChatRoom이 null이 아니라면
            ChatRoom chatRoom = existingChatRoom.get();


                return ChatRoomResponseDto.builder()
                        // TODO 수정 필요
                        // buyer, seller, product가 모두 동일하면 존재하는 채팅방 중 아무 거나 가져온다?
                        .roomId(chatRoom.getId())
                        .buyerId(chatRoomCreateDto.getBuyerId())
                        .sellerId(chatRoomCreateDto.getSellerId())
                        .build();
            }




        // TODO : DTO를 아래와 같이 만들면 매번 ChatRoom이 생성되지 않을까?, 같은 거
        // TODO : ChatRoom Id값 랜덤생성 필요.
        ChatRoom chatRoom = ChatRoom.builder()
                .product(product)
                .buyer(buyer)
                .seller(seller)
                .build();
        chatRoom = chatRoomRepository.save(chatRoom);

        return ChatRoomResponseDto.builder()
                .roomId(chatRoom.getId())
                .buyerId(chatRoomCreateDto.getBuyerId())
                .sellerId(chatRoomCreateDto.getSellerId())
                .build();
    }


    public ChatRoomResponseDto getChatRoom(UUID roomId, HttpSession httpSession) {

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new ApiException(ChatErrorCode.NO_EXIST_CHATROOM)
        );

        UUID chatRoomId = chatRoom.getId();

        // ChatRoom 객체로 UserRole을 조회하는 메서드 정의 필요
//        List<User> users = userRepository.findUserByChatRoom(chatRoomId).orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 업슶니다."));

        /**n
         * seller냐 buyer냐에 따라서 분기가 필요하지 않을까?n
         * 1. 똑같은 URL(/chat/chatRoom/{roomId}를 통n해 입장한다.
         * 2. roomId로 채팅방을 조회한다.
         * 3. 채팅방 객체에는 User(Seller), User(Buyer)가 저장되어 있다.
         * 4. 해당 API에 접근한 주체가 누구인지는 어떻게 파악하나?
         * - 인증의 문제n
         */
        User user = (User) httpSession.getAttribute("user");
        UserRole userRole = user.getUserRole();
        Long senderId = user.getId();


        return ChatRoomResponseDto.builder()
                .roomId(chatRoom.getId())
                .buyerId(chatRoom.getBuyer().getId())
                .sellerId(chatRoom.getSeller().getId())
                .userRole(userRole)
                .senderId(senderId)
                .build();
    }

}
