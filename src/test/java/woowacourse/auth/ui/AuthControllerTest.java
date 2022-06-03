package woowacourse.auth.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import woowacourse.auth.dto.request.MemberCreateRequest;
import woowacourse.auth.dto.request.MemberUpdateRequest;
import woowacourse.auth.dto.request.PasswordCheckRequest;
import woowacourse.auth.dto.request.PasswordUpdateRequest;
import woowacourse.auth.dto.response.LoginResponse;
import woowacourse.auth.dto.response.MemberResponse;
import woowacourse.auth.dto.response.PasswordCheckResponse;
import woowacourse.auth.exception.AuthorizationException;
import woowacourse.auth.support.TokenProvider;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    private static final String TOKEN = "access_token";
    private static final String PAYLOAD = "abc@woowahan.com";

    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private HandlerInterceptor handlerInterceptor;
    @MockBean
    private HandlerMethodArgumentResolver handlerMethodArgumentResolver;
    @MockBean
    private AuthService authService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("회원 가입에 성공한다. - 201 Created")
    @Test
    void signUp_Created() throws Exception {
        MemberCreateRequest requestBody = new MemberCreateRequest("abc@woowahan.com", "1q2w3e4r!", "우아한");

        willDoNothing().given(authService)
                .save(any());

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated());

        verify(authService, times(1))
                .save(any());
    }

    @DisplayName("형식에 맞지 않는 정보로 회원 가입에 실패한다. - 400 Bad Request")
    @ParameterizedTest
    @CsvSource({"abc,1q2w3e4r!,닉네임", "abc@woowahan.com,1q2w3e4r,닉네임", "abc@woowahan.com,1q2w3e4r!,잘못된닉네임"})
    void signUp_BadRequest(String email, String password, String nickname) throws Exception {
        MemberCreateRequest requestBody = new MemberCreateRequest(email, password, nickname);

        willDoNothing().given(authService)
                .save(any());

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());
        verify(authService, times(0))
                .save(any());
    }

    @DisplayName("이메일 중복 체크를 한다. - 200 Ok")
    @Test
    void checkUniqueEmail() throws Exception {
        given(authService.existsEmail(any()))
                .willReturn(true);

        mockMvc.perform(get("/api/members/check-email?email=" + "abc@woowahan.com"))
                .andExpect(status().isOk());
        verify(authService, times(1))
                .existsEmail("abc@woowahan.com");
    }

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

        mockMvc.perform(post("/api/members/password-check")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
        verify(authService, times(1))
                .checkPassword(any(), any());
    }

    @DisplayName("회원 정보를 조회한다. - 200 Ok")
    @Test
    void showMember_Ok() throws Exception {
        MemberResponse memberResponse = new MemberResponse("abc@woowahan.com", "nickname");

        given(authService.findMember(any()))
                .willReturn(memberResponse);
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);
        given(tokenProvider.validateToken(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.supportsParameter(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(PAYLOAD);

        mockMvc.perform(get("/api/members/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", TOKEN))
                .andExpect(status().isOk());

        verify(authService, times(1))
                .findMember(PAYLOAD);
    }

    @DisplayName("인증 없이 회원 정보를 조회할 수 없다. - 401 Unauthorized")
    @Test
    void showMember_Unauthorized() throws Exception {
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willThrow(new AuthorizationException("로그인이 필요합니다."));

        mockMvc.perform(get("/api/members/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authService, times(0))
                .findMember(any());
    }

    @DisplayName("회원 정보를 수정한다. - 204 No Content")
    @Test
    void updateMember_NoContent() throws Exception {
        MemberUpdateRequest requestBody = new MemberUpdateRequest("우아한");

        willDoNothing().given(authService)
                        .updateMember(any(), any());
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);
        given(tokenProvider.validateToken(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.supportsParameter(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(PAYLOAD);

        mockMvc.perform(patch("/api/members/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNoContent());

        verify(authService, times(1))
                .updateMember(eq(PAYLOAD), any());
    }

    @DisplayName("인증 없이 회원 정보를 수정할 수 없다. - 401 Unauthorized")
    @Test
    void updateMember_Unauthorized() throws Exception {
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willThrow(new AuthorizationException("로그인이 필요합니다."));

        mockMvc.perform(patch("/api/members/me")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authService, times(0))
                .updateMember(any(), any());
    }

    @DisplayName("올바르지 않은 형식의 닉네임으로 회원 정보를 수정할 수 없다. - 400 Bad Request")
    @Test
    void updateMember_InvalidNickname_BadRequest() throws Exception {
        MemberUpdateRequest requestBody = new MemberUpdateRequest("잘못된닉네임");

        willDoNothing().given(authService)
                .updateMember(any(), any());
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);
        given(tokenProvider.validateToken(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.supportsParameter(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(PAYLOAD);

        mockMvc.perform(patch("/api/members/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());

        verify(authService, times(0))
                .updateMember(any(), any());
    }

    @DisplayName("비밀번호를 수정한다. - 204 No Content")
    @Test
    void updatePassword_NoContent() throws Exception {
        PasswordUpdateRequest requestBody = new PasswordUpdateRequest("1q2w3e4r!");

        willDoNothing().given(authService)
                .updatePassword(any(), any());
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);
        given(tokenProvider.validateToken(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.supportsParameter(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(PAYLOAD);

        mockMvc.perform(patch("/api/members/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNoContent());

        verify(authService, times(1))
                .updatePassword(eq(PAYLOAD), any());
    }

    @DisplayName("인증 없이 비밀번호를 수정할 수 없다. - 401 Unauthorized")
    @Test
    void updatePassword_Unauthorized() throws Exception {
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willThrow(new AuthorizationException("로그인이 필요합니다."));

        mockMvc.perform(patch("/api/members/password")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

        verify(authService, times(0))
                .updatePassword(any(), any());
    }

    @DisplayName("올바르지 않은 형식의 비밀번호로 회원 정보를 수정할 수 없다. - 400 Bad Request")
    @Test
    void updatePassword_InvalidPassword_BadRequest() throws Exception {
        PasswordUpdateRequest requestBody = new PasswordUpdateRequest("1q2w3e4r");

        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);
        given(tokenProvider.validateToken(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.supportsParameter(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(PAYLOAD);

        mockMvc.perform(patch("/api/members/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());

        verify(authService, times(0))
                .updatePassword(any(), any());
    }

    @DisplayName("회원을 삭제한다. - 204 No Content")
    @Test
    void deleteMember_NoContent() throws Exception {
        willDoNothing().given(authService)
                .delete(any());
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willReturn(true);
        given(tokenProvider.validateToken(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.supportsParameter(any()))
                .willReturn(true);
        given(handlerMethodArgumentResolver.resolveArgument(any(), any(), any(), any()))
                .willReturn(PAYLOAD);

        mockMvc.perform(delete("/api/members/me")
                        .header("Authorization", TOKEN))
                .andExpect(status().isNoContent());

        verify(authService, times(1))
                .delete(PAYLOAD);
    }

    @DisplayName("인증 없이 회원을 삭제할 수 없다. - 401 Unauthorized")
    @Test
    void deleteMember_Unauthorized() throws Exception {
        given(handlerInterceptor.preHandle(any(), any(), any()))
                .willThrow(new AuthorizationException("로그인이 필요합니다."));

        mockMvc.perform(delete("/api/members/me"))
                .andExpect(status().isUnauthorized());

        verify(authService, times(0))
                .delete(any());
    }
}
