package woowacourse.shoppingcart.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.shoppingcart.dto.CustomerRequest;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/truncate.sql")
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("회원 가입 시 로그인 아이디가 공백일 경우 Bad Request를 반환한다.")
    void loginId_blank(String loginId) throws Exception {
        // given
        CustomerRequest request = new CustomerRequest(loginId, 페퍼_이름, 페퍼_비밀번호);
        String requestContent = objectMapper.writeValueAsString(request);

        // when
        final ResultActions response = mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print());

        // then
        response.andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("회원 가입 시 이름이 공백일 경우 Bad Request를 반환한다.")
    void name_blank(String name) throws Exception {
        // given
        CustomerRequest request = new CustomerRequest(페퍼_아이디, name, 페퍼_비밀번호);
        String requestContent = objectMapper.writeValueAsString(request);

        // when
        final ResultActions response = mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print());

        // then
        response.andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("회원 가입 시 비밀번호가 공백일 경우 Bad Request를 반환한다.")
    void password_blank(String password) throws Exception {
        // given
        CustomerRequest request = new CustomerRequest(페퍼_아이디, 페퍼_이름, password);
        String requestContent = objectMapper.writeValueAsString(request);

        // when
        final ResultActions response = mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andDo(print());

        // then
        response.andExpect(status().isBadRequest());
    }
}
