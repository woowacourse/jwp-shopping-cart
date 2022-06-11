package woowacourse.auth.ui;

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
import woowacourse.auth.ui.dto.LoginRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private AuthenticationPrincipalConfig authenticationPrincipalConfig;

    @MockBean
    private AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    @Test
    @DisplayName("아이디, 페스워드를 통해서 로그인을 하면 토큰을 발급받는다.")
    void login() throws Exception {
        // given & when
        ResultActions 토큰_응답 = postLogin("email@email.com", "password123A!");

        // then
        assertThat(토큰_응답.andExpect(status().isOk()));
    }

    @Test
    @DisplayName("로그인 시 공백을 입력하면 예외가 발생한다.")
    void checkRequestBlank() throws Exception {
        // given & when
        ResultActions 이메일_공백 = postLogin(null, "password123A!");
        ResultActions 비밀번호_공백 = postLogin("email@email.com", null);

        // then
        assertAll(
                () -> 이메일_공백.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 이메일은 공백일 수 없습니다.")),
                () -> 비밀번호_공백.andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message").value("[ERROR] 비밀번호는 공백일 수 없습니다."))
        );
    }

    private ResultActions postLogin(String email, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest(email, password);
        return mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        objectMapper.writeValueAsString(loginRequest)
                ));
    }
}
