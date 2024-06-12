package shppingmall.commerce.chat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shppingmall.commerce.chat.dto.ChatRoomCreateDto;
import shppingmall.commerce.chat.dto.ChatRoomResponseDto;
import shppingmall.commerce.chat.entity.ChatRoom;
import shppingmall.commerce.chat.repository.ChatRoomRepository;
import shppingmall.commerce.product.entity.Product;
import shppingmall.commerce.product.repository.ProductRepository;
import shppingmall.commerce.user.entity.User;
import shppingmall.commerce.user.entity.UserRole;
import shppingmall.commerce.user.repository.UserRepository;

import java.util.List;
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
                orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 존재하지 않습니다."));

        // buyer 검증
        if (!buyer.getUserRole().equals(UserRole.BUYER)) {
            throw new IllegalArgumentException("회원의 정보가 잘못 되었습니다.");
        }
        User seller = userRepository.findById(chatRoomCreateDto.getSellerId()).
                orElseThrow(() -> new IllegalArgumentException("해당하는 회원이 존재하지 않습니다."));
        // seller 검증
        if (!seller.getUserRole().equals(UserRole.SELLER)) {
            throw new IllegalArgumentException("회원의 정보가 잘못 되었습니다.");
        }

        Product product = productRepository.findById(chatRoomCreateDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 존재하지 않습니다."));


        // 아래와 같이 로직을 작성하면 Optional 안에 null이 아닌 빈 객체를 담고 있을 수도 있지 않을까?
        Optional<List<ChatRoom>> existingChatRoom = chatRoomRepository.findByBuyerAndSellerAndProduct(buyer, seller, product);

        // TODO: 로직 변경 필요.
        if (existingChatRoom.isPresent()) {  // existingChatRoom이 null이 아니라면
            List<ChatRoom> chatRooms = existingChatRoom.get();

            if (chatRooms.size() > 0) {
                return ChatRoomResponseDto.builder()
                        // TODO 수정 필요
                        // buyer, seller, product가 모두 동일하면 존재하는 채팅방 중 아무 거나 가져온다?
                        .id(existingChatRoom.get().get(0).getId())
                        .buyerId(chatRoomCreateDto.getBuyerId())
                        .sellerId(chatRoomCreateDto.getSellerId())
                        .build();
            }

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
                .id(chatRoom.getId())
                .buyerId(chatRoomCreateDto.getBuyerId())
                .sellerId(chatRoomCreateDto.getSellerId())
                .build();
    }


    public ChatRoomResponseDto getChatRoom(UUID roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 채팅방이 존재하지 않습니다.")
        );


        return ChatRoomResponseDto.builder()
                .id(chatRoom.getId())
                .buyerId(chatRoom.getBuyer().getId())
                .sellerId(chatRoom.getSeller().getId())
                .build();
    }

}
