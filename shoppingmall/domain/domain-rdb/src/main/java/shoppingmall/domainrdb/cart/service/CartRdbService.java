package shoppingmall.domainrdb.cart.service;

import shoppingmall.domainrdb.cart.entity.Cart;
import shoppingmall.domainrdb.cart.entity.CartProduct;
import shoppingmall.domainrdb.cart.repository.CartProductRepository;
import shoppingmall.domainrdb.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.common.exception.ApiException;
import shoppingmall.common.exception.domain.CartErrorCode;
import shoppingmall.common.exception.domain.ProductErrorCode;
import shoppingmall.common.exception.domain.UserErrorCode;
import shoppingmall.domainrdb.common.annotation.DomainService;
import shoppingmall.domainrdb.product.entity.Product;
import shoppingmall.domainrdb.product.repository.ProductRepository;
import shoppingmall.domainrdb.user.entity.User;
import shoppingmall.domainrdb.user.repository.UserRepository;

import java.util.Map;
import java.util.stream.Collectors;

@DomainService
@RequiredArgsConstructor
public class CartRdbService {
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    //Cart에 상품을 추가한다.
    @Transactional
    public Map<Long, Integer> addProductToCart(final Long cartId, final Map<Long, Integer> productQuantityMap) {
        // TODO : 예외정의 필요
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ApiException(CartErrorCode.NO_EXIST_CART));
        return productQuantityMap.entrySet().stream()
                .collect(Collectors.toMap(productQuantityEntry -> {
                            Product product = productRepository.findById(productQuantityEntry.getKey()).orElseThrow(() -> new ApiException(ProductErrorCode.PRODUCT_NOT_FOUND));
                            int quantity = productQuantityEntry.getValue();
                            CartProduct cartProduct = CartProduct.builder()
                                    .cart(cart)
                                    .product(product)
                                    .quantity(quantity)
                                    .build();
                            cartProductRepository.save(cartProduct);
                            return cartProduct.getId();
                        }, Map.Entry::getValue

                ));
    }


    // request에서 userId를 추출할 수 있으면 user를 조회후 cart생성하고 아닐 시 그냥 cart를 생성한다.
    @Transactional
    public Long createCart(final Long userId) {

        if (userId != null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(UserErrorCode.NO_EXIST_USER));
            Cart cartWithUser = Cart.builder()
                    .user(user)
                    .build();
            Cart savedCartUser = cartRepository.save(cartWithUser);
            return savedCartUser.getId();

        } else {
            Cart cartWithOutUser = Cart.builder()
                    .build();
            Cart savedCartWithOutUser = cartRepository.save(cartWithOutUser);
            return savedCartWithOutUser.getId();
        }
    }

//    public


}


