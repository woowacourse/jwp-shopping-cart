package woowacourse.helper.restdocs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.ui.AuthController;
import woowacourse.member.application.MemberService;
import woowacourse.member.ui.MemberController;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.application.OrderService;
import woowacourse.shoppingcart.application.ProductService;
import woowacourse.shoppingcart.ui.CartItemController;
import woowacourse.shoppingcart.ui.OrderController;
import woowacourse.shoppingcart.ui.ProductController;

@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest({
        MemberController.class,
        AuthController.class,
        CartItemController.class,
        OrderController.class,
        ProductController.class
})
public abstract class RestDocsTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected ProductService productService;

    @MockBean
    protected OrderService orderService;

    @MockBean
    protected CartService cartService;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;
}
