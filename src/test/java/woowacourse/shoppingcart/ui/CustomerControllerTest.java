package woowacourse.shoppingcart.ui;

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
import woowacourse.auth.config.AuthenticationPrincipalConfig;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.application.CustomerService;
import woowacourse.shoppingcart.ui.dto.CustomerChangePasswordRequest;
import woowacourse.shoppingcart.ui.dto.CustomerChangeRequest;
import woowacourse.shoppingcart.ui.dto.CustomerSignUpRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @MockBean
    private AuthenticationPrincipalConfig authenticationPrincipalConfig;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("아이디, 패스워드, 닉네임을 통해서 회원가입을 할 수 있다.")
    void saveCustomer() throws Exception {
        // given & when
        ResultActions 회원가입_응답 = postCustomers("email@email.com", "password123!Q", "rookie");

        // then
        assertThat(회원가입_응답.andExpect(status().isOk()));
    }

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
                        .andExpect(jsonPath("$.message").value("[ERROR] 이메일은 공백일 수 없습니다.")),
                () -> 이메일_공백2.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 이메일은 공백일 수 없습니다.")),
                () -> 비밀번호_공백1.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 비밀번호는 공백일 수 없습니다.")),
                () -> 비밀번호_공백2.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 비밀번호는 공백일 수 없습니다.")),
                () -> 닉네임_공백1.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 닉네임은 공백일 수 없습니다.")),
                () -> 닉네임_공백2.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 닉네임은 공백일 수 없습니다."))
        );
    }

    @Test
    @DisplayName("회원정보 번경시 닉네임에 공백을 입력하면 예외가 발생한다.")
    void checkUpdateRequestBlank() throws Exception {
        // given & when
        ResultActions 닉네임_공백1 = patchCustomers(null);
        ResultActions 닉네임_공백2 = patchCustomers(" ");
        ResultActions 이전_패스워드_공백1 = patchCustomersPassword(null, "password123!Q");
        ResultActions 이전_패스워드_공백2 = patchCustomersPassword(" ", "password123!Q");
        ResultActions 새로운_패스워드_공백1 = patchCustomersPassword("password123!Q", null);
        ResultActions 새로운_패스워드_공백2 = patchCustomersPassword("password123!Q", " ");
        // then
        assertAll(
                () -> 닉네임_공백1.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 닉네임은 공백일 수 없습니다.")),
                () -> 닉네임_공백2.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 닉네임은 공백일 수 없습니다.")),
                () -> 이전_패스워드_공백1.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 이전 비밀번호는 공백일 수 없습니다.")),
                () -> 이전_패스워드_공백2.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 이전 비밀번호는 공백일 수 없습니다.")),
                () -> 새로운_패스워드_공백1.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 새로운 비밀번호는 공백일 수 없습니다.")),
                () -> 새로운_패스워드_공백2.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 새로운 비밀번호는 공백일 수 없습니다."))
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

    private ResultActions patchCustomers(String nickname) throws Exception {
        CustomerChangeRequest customerChangeRequest = new CustomerChangeRequest(nickname);
        return mockMvc.perform(patch("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(customerChangeRequest)
                ));
    }

    private ResultActions patchCustomersPassword(String prevPassword, String newPassword) throws Exception {
        CustomerChangePasswordRequest customerChangePasswordRequest = new CustomerChangePasswordRequest(prevPassword, newPassword);
        return mockMvc.perform(patch("/api/customers/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(customerChangePasswordRequest)
                ));
    }
}
