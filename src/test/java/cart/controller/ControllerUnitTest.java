package cart.controller;

import cart.controller.auth.BasicAuthorizeInterceptor;
import cart.controller.auth.LoginIdArgumentResolver;
import cart.persistance.dao.CartDao;
import cart.persistance.dao.MemberDao;
import cart.persistance.dao.ProductDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@Import({CartController.class, ProductController.class, ExceptionController.class, ViewController.class})
public class ControllerUnitTest {

    protected static final String CODE = "Basic dXNlcjFAd29vd2EuY29tOjEyMzQ1Ng==";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected CartDao cartDao;

    @MockBean
    protected MemberDao memberDao;

    @MockBean
    protected ProductDao productDao;

    @SpyBean
    protected BasicAuthorizeInterceptor interceptor;

    @SpyBean
    protected LoginIdArgumentResolver argumentResolver;
}
