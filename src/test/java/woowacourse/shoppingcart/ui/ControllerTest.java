package woowacourse.shoppingcart.ui;

import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.shoppingcart.auth.application.AuthService;
import woowacourse.shoppingcart.auth.ui.AuthController;
import woowacourse.shoppingcart.customer.application.CustomerService;
import woowacourse.shoppingcart.customer.ui.CustomerController;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.support.JwtTokenProvider;

@WebMvcTest({
        AuthController.class,
        CustomerController.class
})
@AutoConfigureRestDocs
abstract class ControllerTest {

    protected static final String HASH = "$2a$10$aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @MockBean
    protected AuthService authService;

    @MockBean
    protected CustomerService customerService;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected void getLoginCustomerByToken(final String token, final Customer customer) {
        given(jwtTokenProvider.validateToken(token))
                .willReturn(true);
        given(jwtTokenProvider.getPayload(token))
                .willReturn(customer.getEmail());

        given(customerService.getByEmail(customer.getEmail()))
                .willReturn(customer);
        given(customerService.isExistEmail(customer.getEmail()))
                .willReturn(true);
    }
}
