package woowacourse.auth.ui;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.auth.dto.LoginResponse;
import woowacourse.shoppingcart.ui.ControllerTest;

@DisplayName("AuthController 단위 테스트")
class AuthControllerTest extends ControllerTest {

    @Test
    @DisplayName("로그인 요청에 성공하면 JWT 토큰을 발급해서 반환한다.")
    void login_validForm_200() throws Exception {
        // given
        final String accessToken = "fake-token";
        final LoginRequest request = new LoginRequest("email@email.com", "1q2w3e4r");
        given(authService.login(request))
                .willReturn(accessToken);

        final String json = objectMapper.writeValueAsString(request);

        final LoginResponse response = new LoginResponse(accessToken);
        final String expected = objectMapper.writeValueAsString(response);

        // when
        final ResultActions perform = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)
                .accept(MediaType.ALL));

        // then
        final String body = perform
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(body).isEqualTo(expected);
    }
}
