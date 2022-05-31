package woowacourse.shoppingcart.ui;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.application.AuthService;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.ui.dto.CustomerSignUpRequest;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private AuthService authService;

    @Test
    @DisplayName("회원가입 시 공백을 입력하면 예외가 발생한다.")
    void checkRequestBlank() throws Exception {
        // given & when
        ResultActions 이메일_공백1 = postCustomers(null, "password123!Q", "rookie");
        ResultActions 이메일_공백2 = postCustomers(" ", "password123!Q", "rookie");
        ResultActions 비밀번호_공백1 = postCustomers("email@email.com", null, "rookie");
        ResultActions 비밀번호_공백2 = postCustomers("email@email.com", " ", "rookie");
        ResultActions 닉네임_공백1 = postCustomers("email@email.com", "password123!Q", null);
        ResultActions 닉네임_공백2 = postCustomers("email@email.com", "password123!Q", " ");
        // then
        assertAll(
                () -> 이메일_공백1.andExpect(status().isBadRequest())
                        .andExpect(content().string("[ERROR] 이메일은 공백일 수 없습니다.")),
                () -> 이메일_공백2.andExpect(status().isBadRequest())
                        .andExpect(content().string("[ERROR] 이메일은 공백일 수 없습니다.")),
                () -> 비밀번호_공백1.andExpect(status().isBadRequest())
                        .andExpect(content().string("[ERROR] 비밀번호는 공백일 수 없습니다.")),
                () -> 비밀번호_공백2.andExpect(status().isBadRequest())
                        .andExpect(content().string("[ERROR] 비밀번호는 공백일 수 없습니다.")),
                () -> 닉네임_공백1.andExpect(status().isBadRequest())
                        .andExpect(content().string("[ERROR] 닉네임은 공백일 수 없습니다.")),
                () -> 닉네임_공백2.andExpect(status().isBadRequest())
                        .andExpect(content().string("[ERROR] 닉네임은 공백일 수 없습니다."))
        );
    }

    private ResultActions postCustomers(String email, String password, String nickname) throws Exception {
        CustomerSignUpRequest customerSignUpRequest = new CustomerSignUpRequest(email, password, nickname);
        return mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(customerSignUpRequest)
                ));
    }
}
