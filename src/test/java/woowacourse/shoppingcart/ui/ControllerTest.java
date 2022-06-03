package woowacourse.shoppingcart.ui;

import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.shoppingcart.application.AuthService;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.support.JwtTokenProvider;

@WebMvcTest({
        AuthController.class,
        CustomerController.class
})
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
