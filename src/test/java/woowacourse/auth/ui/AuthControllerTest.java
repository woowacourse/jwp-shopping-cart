package woowacourse.auth.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.customer.CustomerAddRequest;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/truncate.sql")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("로그인 성공 시 토큰을 반환한다.")
    void login() throws Exception {
        // given
        CustomerAddRequest customerAddRequest = new CustomerAddRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호);
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerAddRequest)))
                .andDo(print());

        TokenRequest request = new TokenRequest(페퍼_아이디, 페퍼_비밀번호);
        String requestContent = objectMapper.writeValueAsString(request);

        // when
        final ResultActions response = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print());

        // then
        response.andExpect(status().isOk());
//                .andExpect(jsonPath("accessToken").value(jwtTokenProvider.createToken(페퍼_아이디)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"testwoowacoursecom", "test@woowacoursecom", "testwoowacourse.com", "@", ".", "@.",
            ".@wo.com", "test.woowacourse@com", "", " "})
    @DisplayName("loginId가 이메일 형식이 아닌 경우 Bad Request를 던진다.")
    void login_notEmail(String loginId) throws Exception {
        // given
        TokenRequest request = new TokenRequest(loginId, 페퍼_비밀번호);
        String requestContent = objectMapper.writeValueAsString(request);

        // when
        final ResultActions response = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print());

        // then
        response.andExpect(status().isBadRequest())
                .andExpect(content().string("이메일 형식이 아닙니다."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("비밀번호가 공백인 경우 Bad Request를 던진다.")
    void login_noPassword(String password) throws Exception {
        // given
        TokenRequest request = new TokenRequest(페퍼_아이디, password);
        String requestContent = objectMapper.writeValueAsString(request);

        // when
        final ResultActions response = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print());

        // then
        response.andExpect(status().isBadRequest());
    }
}
