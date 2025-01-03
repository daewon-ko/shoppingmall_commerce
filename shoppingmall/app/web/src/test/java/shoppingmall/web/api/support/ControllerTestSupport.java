package shoppingmall.web.api.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import shoppingmall.domainrdb.domain.cart.service.CartService;
import shoppingmall.domainrdb.category.service.CategoryService;
import shoppingmall.domainrdb.domain.chat.service.ChatRoomService;
import shoppingmall.domainrdb.domain.message.service.MessageService;
import shoppingmall.domainrdb.domain.order.service.OrderService;
import shoppingmall.domainrdb.product.service.ProductService;
import shoppingmall.domainrdb.user.repository.UserRepository;
import shoppingmall.domainrdb.user.service.UserService;
import shoppingmall.web.api.cart.controller.CartController;
import shoppingmall.web.api.category.controller.CategoryController;
import shoppingmall.web.api.chat.ChatController;
import shoppingmall.web.api.order.OrderController;
import shoppingmall.web.api.product.ProductController;
import shoppingmall.web.api.user.AuthController;

@WebMvcTest(controllers = {
        ProductController.class,
        AuthController.class,
        OrderController.class,
        ChatController.class,
        CategoryController.class,
        CartController.class,
})
//@ContextConfiguration(classes = {
//        ProductService.class,
//        CartService.class,
//        CategoryService.class,
//        ChatRoomService.class,
//        OrderService.class,
//        MessageService.class,
//        UserService.class
//})
//@ComponentScan(basePackages = {
//        "shoppingmall.core.domain.cart.service",
//        "shoppingmall.core.domain.category.service",
//        "shoppingmall.core.domain.chat.service",
//        "shoppingmall.core.domain.message.service",
//        "shoppingmall.core.domain.order.service",
//        "shoppingmall.core.domain.product.service",
//        "shoppingmall.core.domain.user.service",
//        "shoppingmall.core.domain.user.repository"
//})
public abstract class ControllerTestSupport {

    @MockBean(JpaMetamodelMappingContext.class)  // Application에서 @EnableJpaAuditing를 사용하므로 JPA관련 설정을 테스트에서도 해주도록 함
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected ProductService productService;
    @MockBean
    protected CartService cartService;
    @MockBean
    protected CategoryService categoryService;
    @MockBean
    protected ChatRoomService chatRoomService;
    @MockBean
    protected OrderService orderService;
    @MockBean
    protected MessageService messageService;
    @MockBean
    protected UserService userService;
    @MockBean
    protected UserRepository userRepository;


}


