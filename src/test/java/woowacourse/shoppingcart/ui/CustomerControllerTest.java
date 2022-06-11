package woowacourse.shoppingcart.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dto.customer.CustomerAddRequest;
import woowacourse.shoppingcart.dto.customer.CustomerDeleteRequest;

@AutoConfigureMockMvc
@SpringBootTest
@Sql("/truncate.sql")
@DisplayName("CustomerController 클래스의")
class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Nested
    @DisplayName("save 메서드는")
    class save {

        @ParameterizedTest
        @ValueSource(strings = {"testwoowacoursecom", "test@woowacoursecom", "testwoowacourse.com", "@", ".", "@.",
                ".@wo.com", "test.woowacourse@com"})
        @NullAndEmptySource
        @DisplayName("로그인 아이디가 이메일 형식이 아니면, Bad Request를 던진다")
        void loginId_notEmail(String loginId) throws Exception {
            // given
            CustomerAddRequest request = new CustomerAddRequest(loginId, 페퍼_이름, 페퍼_비밀번호);
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
        @NullAndEmptySource
        @DisplayName("이름이 공백이면, Bad Request를 던진다.")
        void name_blank(String name) throws Exception {
            // given
            CustomerAddRequest request = new CustomerAddRequest(페퍼_아이디, name, 페퍼_비밀번호);
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
        @DisplayName("비밀번호가 공백이면, Bad Request를 던진다.")
        void password_blank(String password) throws Exception {
            // given
            CustomerAddRequest request = new CustomerAddRequest(페퍼_아이디, 페퍼_이름, password);
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

    @Nested
    @DisplayName("update 메서드는")
    class update {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("이름이 공백이면, Bad Request를 던진다.")
        void name_blank(String name) throws Exception {
            // given
            CustomerAddRequest request = new CustomerAddRequest(페퍼_아이디, name, 페퍼_비밀번호);
            String requestContent = objectMapper.writeValueAsString(request);

            // when
            final ResultActions response = mockMvc.perform(put("/customers/me")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenProvider.createToken(페퍼_아이디))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestContent))
                    .andDo(print());

            // then
            response.andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("비밀번호가 공백이면, Bad Request를 던진다.")
        void password_blank(String password) throws Exception {
            // given
            CustomerAddRequest request = new CustomerAddRequest(페퍼_아이디, 페퍼_이름, password);
            String requestContent = objectMapper.writeValueAsString(request);

            // when
            final ResultActions response = mockMvc.perform(put("/customers/me")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenProvider.createToken(페퍼_아이디))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestContent))
                    .andDo(print());

            // then
            response.andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class delete {

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("비밀번호가 공백이면, Bad Request를 던진다.")
        void password_blank(String password) throws Exception {
            // given
            CustomerDeleteRequest request = new CustomerDeleteRequest(password);
            String requestContent = objectMapper.writeValueAsString(request);

            // when
            final ResultActions response = mockMvc.perform(delete("/customers/me")
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtTokenProvider.createToken(페퍼_아이디))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestContent))
                    .andDo(print());

            // then
            response.andExpect(status().isBadRequest());
        }
    }
}
