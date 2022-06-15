package woowacourse.shoppingcart.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import woowacourse.auth.service.AuthService;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.service.OrderService;
import woowacourse.shoppingcart.service.SpringCustomerService;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
    private static final String AUTHORIZATION = "authorization";

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected SpringCustomerService customerService;
    @MockBean
    protected AuthService authService;
    @MockBean
    protected OrderService orderService;
    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    protected MockHttpServletRequestBuilder getWithToken(String url, String token) {
        return get(url)
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
    }

    protected MockHttpServletRequestBuilder postWithBody(String url, Object body)
            throws JsonProcessingException {
        return post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(body));
    }

    protected MockHttpServletRequestBuilder postWithTokenAndBody(String url, String accessToken, Object body)
            throws JsonProcessingException {
        return post(url)
                .header(AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(body));
    }

    protected MockHttpServletRequestBuilder updateWithToken(String url, String token, Object body)
            throws JsonProcessingException {
        return put(url)
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(body));
    }

    protected MockHttpServletRequestBuilder deleteWithToken(String url, String token, Object body)
            throws JsonProcessingException {
        return delete(url)
                .header(AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(objectMapper.writeValueAsString(body));
    }
}
