package shoppingmall.domain.domain.cart.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shoppingmall.domain.domain.cart.dto.dto.request.AddCartProductRequestDto;
import shoppingmall.domain.domain.cart.dto.dto.request.AddCartRequestDto;
import shoppingmall.domain.domain.cart.dto.dto.request.CreateCartRequestDto;
import shoppingmall.domain.domain.cart.entity.Cart;
import shoppingmall.domain.domain.cart.entity.CartProduct;
import shoppingmall.domain.domain.cart.repository.CartProductRepository;
import shoppingmall.domain.domain.cart.repository.CartRepository;
import shoppingmall.domain.domain.product.entity.Product;
import shoppingmall.domain.domain.product.repository.ProductRepository;
import shoppingmall.domain.domain.user.entity.User;
import shoppingmall.domain.domain.user.entity.UserRole;
import shoppingmall.domain.domain.user.repository.UserRepository;
import shoppingmall.domain.support.IntegrationTestSupport;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class CartServiceTest extends IntegrationTestSupport {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartProductRepository cartProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        cartProductRepository.deleteAllInBatch();
        cartRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @DisplayName("회원이 있는 장바구니를 생성한다.")
    @Test
    void createCartTest() {
        //given

        User createdUser = createUser("고대원");


        CreateCartRequestDto createCartRequest = getCreateCartRequestDto(createdUser);

        //when
        cartService.createCart(createCartRequest);
        List<Cart> result = cartRepository.findAll();

        //then
        assertThat(result)
                .hasSize(1)
                .extracting(Cart::getUser)
                .containsExactly(createdUser);


    }

    private static CreateCartRequestDto getCreateCartRequestDto(User createdUser) {
        CreateCartRequestDto createCartRequest = CreateCartRequestDto.builder()
                .userId(createdUser.getId())
                .build();
        return createCartRequest;
    }

    @DisplayName("회원이 없는 장바구니를 조회 시, 회원은 null값을 반환한다.")
    @Test
    void createCartWithOutUser() {
        //given
        CreateCartRequestDto requestDto = CreateCartRequestDto.builder()
                .build();
        //when
        cartService.createCart(requestDto);
        List<Cart> result = cartRepository.findAll();

        //then

        assertThat(result).hasSize(1)
                .first()
                .extracting(Cart::getUser)
                .isNull();

    }

    @DisplayName("장바구니에 여러 상품을 추가하면, 장바구니에 상품이 올바르게 추가된다.")
    @Test
    void addProductsToCart() {
        //given

        // 장바구니 생성
        CreateCartRequestDto createCartRequest = CreateCartRequestDto
                .builder()
                .build();
        Long cartId = cartService.createCart(createCartRequest);


        // 상품 생성 및 저장
        Product productA = createProduct(1000, "productA");
        productRepository.save(productA);
        Product productB = createProduct(1001, "productB");
        productRepository.save(productB);

        AddCartProductRequestDto cartProductA = createCartProductRequest(productA, 100);
        AddCartProductRequestDto cartProductB = createCartProductRequest(productB, 100);


        AddCartRequestDto addCartRequest = createCartRequest(cartId, cartProductA, cartProductB);

        //when
        cartService.addProductToCart(addCartRequest);
        List<CartProduct> cartProducts = cartProductRepository.findAll();

        //then
        assertThat(cartProducts).hasSize(2)
                .extracting(CartProduct::getProduct, CartProduct::getQuantity)
                .contains(
                        tuple(productA, 100),
                        tuple(productB, 100)
                );

    }


    private static AddCartRequestDto createCartRequest(Long cartId, AddCartProductRequestDto ... cartProduct) {
        AddCartRequestDto addCartRequest = AddCartRequestDto.builder()
                .cartId(cartId)
                .cartProductRequestDtoList((Arrays.stream(cartProduct).toList()))
                .build();
        return addCartRequest;
    }

    private static AddCartProductRequestDto createCartProductRequest(Product product, int quantity) {
        return  AddCartProductRequestDto.builder()
                .productId(product.getId())
                .quantity(quantity)
                .build();
    }


    private User createUser(String name) {
        User user = User.builder()
                .name(name)
                .userRole(UserRole.BUYER)
                .build();
        return userRepository.save(user);
    }

    private static Product createProduct(int price, String name) {
        Product product = Product.builder()
                .price(price)
                .name(name)
                .build();
        return product;
    }


}