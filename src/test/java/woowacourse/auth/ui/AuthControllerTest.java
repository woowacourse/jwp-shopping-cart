package woowacourse.auth.ui;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.ShoppingCartFixture.LOGIN_URI;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.ui.ControllerTest;

class AuthControllerTest extends ControllerTest {

    @DisplayName("로그인 시도 시, 이메일이 비어 있으면 400을 응답한다.")
    @Test
    void getTokenWithBlankEmailShouldFail() throws Exception {
        final Map<String, String> params = Map.of("password", "1234");

        // when then
        mockMvc.perform(postWithBody(LOGIN_URI, params))
                .andDo(print())
                .andExpect(content().string("이메일은 빈 값일 수 없습니다."))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("로그인 시도 시, 비밀번호가 비어 있으면 400을 응답한다. ")
    @Test
    void getTokenWithBlankPasswordShouldFail() throws Exception {
        final Map<String, String> params = Map.of("email", "email@email");

        // when then
        mockMvc.perform(postWithBody(LOGIN_URI, params))
                .andDo(print())
                .andExpect(content().string("비밀번호는 빈 값일 수 없습니다."))
                .andExpect(status().isBadRequest());
    }
}
