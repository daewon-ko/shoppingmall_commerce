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
