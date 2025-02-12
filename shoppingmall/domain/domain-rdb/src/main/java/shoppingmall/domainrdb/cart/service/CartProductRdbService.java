package shoppingmall.domainrdb.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.domainrdb.cart.CartProductDomain;
import shoppingmall.domainrdb.cart.repository.CartProductRepository;
import shoppingmall.domainrdb.common.annotation.DomainRdbService;
import shoppingmall.domainrdb.mapper.CartProductEntityMapper;

import java.util.List;
import java.util.stream.Collectors;

@DomainRdbService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartProductRdbService {
    private final CartProductRepository cartProductRepository;

    public List<CartProductDomain> findAllCartProductsByCartId(final Long cartId) {
        return cartProductRepository.findAllByCartId(cartId)
                .orElseThrow(() -> new IllegalStateException("Cart is Empty"))
                .stream()
                .map(cartProduct -> {
                    CartProductDomain cartProductDomain = CartProductEntityMapper.toCartProductDomain(cartProduct);
                    return cartProductDomain;
                }).collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void deleteAllByCartId(final Long cartId) {
        cartProductRepository.deleteAllByCartId(cartId);
    }
}
