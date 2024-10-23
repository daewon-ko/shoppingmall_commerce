package shppingmall.commerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import shppingmall.commerce.cart.controller.CartController;
import shppingmall.commerce.cart.service.CartService;
import shppingmall.commerce.category.controller.CategoryController;
import shppingmall.commerce.category.service.CategoryService;
import shppingmall.commerce.chat.controller.ChatController;
import shppingmall.commerce.chat.service.ChatRoomService;
import shppingmall.commerce.message.service.MessageService;
import shppingmall.commerce.order.controller.OrderController;
import shppingmall.commerce.order.service.OrderService;
import shppingmall.commerce.product.controller.ProductController;
import shppingmall.commerce.product.service.ProductService;
import shppingmall.commerce.user.controller.AuthController;
import shppingmall.commerce.user.repository.UserRepository;
import shppingmall.commerce.user.service.UserService;

@WebMvcTest(controllers = {
        ProductController.class,
        AuthController.class,
        OrderController.class,
        ChatController.class,
        CategoryController.class,
        CartController.class,
})
@MockBean(JpaMetamodelMappingContext.class)  // Application에서 @EnableJpaAuditing를 사용하므로 JPA관련 설정을 테스트에서도 해주도록 함
public class ControllerTestSupport {
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
