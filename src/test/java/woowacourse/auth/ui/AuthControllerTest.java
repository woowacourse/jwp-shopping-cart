package woowacourse.auth.ui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.dto.TokenResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @DisplayName("회원 로그인에 성공하면 상태 코드 200과 토큰을 반환한다.")
    @Test
    void login() throws Exception {
        // given
        TokenRequest tokenRequest = new TokenRequest("test@test.com", "Test1234!@");
        String token = "testToken";
        when(authService.login(any(TokenRequest.class)))
                .thenReturn(new TokenResponse(token));
        // when
        ResultActions perform = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tokenRequest)));
        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").exists());
    }

    @DisplayName("회원 로그인에 실패하면 상태코드 400과 에러 메세지를 반환한다.")
    @Test
    void login_fail() throws Exception {
        // given
        TokenRequest tokenRequest = new TokenRequest("test@test.com", "Invalid12!@");
        String token = "testToken";
        when(authService.login(any(TokenRequest.class)))
                .thenThrow(new InvalidCustomerException("오류"));
        // when
        ResultActions perform = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tokenRequest)));
        // then
        perform.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").exists());
    }
}
