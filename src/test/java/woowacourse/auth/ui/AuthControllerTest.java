package woowacourse.auth.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.request.LoginRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.dto.response.PasswordCheckResponse;
import woowacourse.auth.support.TokenProvider;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    private static final String TOKEN = "access_token";
    private static final Long MEMBER_ID = 1L;

    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private HandlerInterceptor handlerInterceptor;
    @MockBean
    private AuthService authService;
    @MockBean
    private HandlerMethodArgumentResolver handlerMethodArgumentResolver;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("로그인에 성공하면 토큰과 닉네임을 반환한다. - 200 Ok")
    @Test
    void login_Ok() throws Exception {
        LoginRequest requestBody = new LoginRequest("abc@woowahan.com", "1q2w3e4r!");

        given(authService.login(any()))
                .willReturn(new LoginResponse(TOKEN, "nickname"));

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        verify(authService, times(1))
                .login(any());
    }

    @DisplayName("로그인에 실패한다. - 400 Bad Request")
    @Test
    void login_BadRequest() throws Exception {
        LoginRequest requestBody = new LoginRequest("abc@woowahan.com", "1q2w3e4r!");

        given(authService.login(any()))
                .willThrow(new IllegalArgumentException("이메일과 비밀번호를 확인해주세요."));

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());
        verify(authService, times(1))
                .login(any());
    }

    @DisplayName("아이디나 비밀번호에 빈 값을 입력하면 로그인에 실패한다. - 400 Bad Request")
    @ParameterizedTest
    @CsvSource({"' ',1q2w3e4r!", "abc@woowahan.com,' '"})
    void loginBlank_BadRequest(String email, String password) throws Exception {
        LoginRequest requestBody = new LoginRequest(email, password);

        given(authService.login(any()))
                .willReturn(new LoginResponse(TOKEN, "nickname"));

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());
        verify(authService, times(0))
                .login(any());
    }

    @DisplayName("비밀번호가 맞는지 검증한다. - 200 OK")
    @Test
    void confirmPassword_Ok() throws Exception {
        PasswordCheckRequest requestBody = new PasswordCheckRequest("1q2w3e4r!");

        given(authService.checkPassword(any(), any()))
                .willReturn(new PasswordCheckResponse(true));
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);
        given(tokenProvider.validateToken(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(1L);

        mockMvc.perform(post("/api/members/password-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        verify(authService, times(1))
                .checkPassword(any(), any());
    }
}
