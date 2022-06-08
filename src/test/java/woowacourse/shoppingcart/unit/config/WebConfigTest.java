package woowacourse.shoppingcart.unit.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.shoppingcart.config.WebConfig.ALLOWED_METHOD_NAMES;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.shoppingcart.customer.application.CustomerService;
import woowacourse.shoppingcart.customer.ui.CustomerController;
import woowacourse.shoppingcart.product.application.ProductService;
import woowacourse.shoppingcart.product.ui.ProductController;
import woowacourse.shoppingcart.support.JwtTokenProvider;

@WebMvcTest({
        ProductController.class,
        CustomerController.class
})
class WebConfigTest {

    @MockBean
    private ProductService productService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void cors() throws Exception {
        mockMvc.perform(
                        options("/products")
                                .header(HttpHeaders.ORIGIN, "http://localhost:8080")
                                .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "GET")
                )
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*"))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHOD_NAMES))
                .andExpect(header().string(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.LOCATION))
                .andDo(print());
    }

    @ParameterizedTest(name = "로그인하지 않은 유저가 {0} {1} 요청을 하면 401을 반환한다.")
    @CsvSource(value = {"GET:/users/me", "DELETE:/users/me", "PUT:/users/me"}, delimiter = ':')
    void interceptor_guestUser_401(final String httpMethod, final String url) throws Exception {
        // when
        final ResultActions perform = mockMvc.perform(request(HttpMethod.resolve(httpMethod), url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isUnauthorized());
    }

    @ParameterizedTest(name = "로그인하지 않은 유저가 {0} {1} 요청을 하면 요청에 성공한다.")
    @CsvSource(value = {"GET:/products", "GET:/products/1"}, delimiter = ':')
    void interceptor_guestUser_success(final String httpMethod, final String url) throws Exception {
        // when
        final ResultActions perform = mockMvc.perform(request(HttpMethod.resolve(httpMethod), url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("로그인하지 않은 OPTIONS 요청은 로그인이 필요한 경로여도 허용한다.")
    void interceptor_optionRequest_allow() throws Exception {
        // when
        final ResultActions perform = mockMvc.perform(options("/users/me")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.ALL)
        ).andDo(print());

        // then
        perform.andExpect(status().isOk());
    }
}
