package woowacourse.shoppingcart.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.application.AuthService;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.dto.CustomerRequest;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private AuthService authService;

    @Test
    void 이름에_대문자가_들어가면_요청_실패() throws Exception {
        // when
        ResultActions response = 회원_가입_요청("Ellie", "12345678");

        // then
        response.andExpect(status().isBadRequest());
    }

    @Test
    void 이름에_언더바_이외의_특수문자가_들어가면_요청_실패() throws Exception {
        // when
        ResultActions response = 회원_가입_요청("ellie-217", "12345678");

        // then
        response.andExpect(status().isBadRequest());
    }

    @Test
    void 비밀번호가_8자_미만이면_요청_실패() throws Exception {
        // when
        ResultActions response = 회원_가입_요청("ellie", "1234");

        // then
        response.andExpect(status().isBadRequest());
    }

    private ResultActions 회원_가입_요청(String name, String password) throws Exception {
        CustomerRequest request = new CustomerRequest(name, password);
        String requestContent = objectMapper.writeValueAsString(request);

        return mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent));
    }
}
