package woowacourse.auth.ui;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.application.AuthService;
import woowacourse.auth.config.WebConfig;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.exception.bodyexception.InvalidLoginException;

@WebMvcTest(controllers = {AuthController.class})
@MockBean(value = {WebConfig.class})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @DisplayName("이메일과 비밀번호가 일치하면 상태코드 200과 token을 반환한다.")
    @Test
    void login_success_200() throws Exception {
        // given
        String email = "email123@email.com";
        String password = "1q2w3e4asdf";

        TokenRequest request = new TokenRequest(email, password);

        String accessToken = "qwerasdf123";
        given(authService.login(request))
                .willReturn(accessToken);

        //when, then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(accessToken));
    }

    @Test
    @DisplayName("이메일이나 비밀번호가 일치하지 않을 때 상태코드 400과 에러코드를 반환한다.")
    void login_fail_400() throws Exception {
        // given
        String email = "email@email.com";
        String password = "qwerasdf123";
        TokenRequest request = new TokenRequest(email, password);

        String errorCode = "1002";
        String message = "로그인에 실패했습니다.";

        given(authService.login(request))
                .willThrow(new InvalidLoginException());

        // when, then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(errorCode))
                .andExpect(jsonPath("$.message").value(message));
    }
}
