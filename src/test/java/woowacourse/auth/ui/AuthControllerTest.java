package woowacourse.auth.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.Fixture.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.Fixture;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.auth.support.JwtTokenProvider;

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
        TokenRequest request = new TokenRequest(페퍼_아이디, 페퍼_비밀번호);
        String requestContent = objectMapper.writeValueAsString(request);
        String token = jwtTokenProvider.createToken(페퍼_아이디);

        // when
        final ResultActions response = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print());

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("accessToken").value(token));
    }
}
