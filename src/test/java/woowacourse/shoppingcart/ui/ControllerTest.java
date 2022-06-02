package woowacourse.shoppingcart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.ui.AuthController;
import woowacourse.shoppingcart.config.LoginCustomerResolver;

@WebMvcTest(AuthController.class)
public abstract class ControllerTest {

    @MockBean
    protected AuthService authService;

    @MockBean
    private LoginCustomerResolver resolver;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
