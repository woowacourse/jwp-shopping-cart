package woowacourse.shoppingcart.ui;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.shoppingcart.dto.customer.CustomerRegisterRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    private static final String NORMAL_EMAIL = "guest@woowa.com";
    private static final String NORMAL_NAME = "guest";
    private static final String NORMAL_PASSWORD = "qwer1234!@#$";

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Email을 검증한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"abcd", "abcd@", "abcd@a", "abcd@a.", "@a", "@a.", "@."})
    void validateEmail(final String email) throws Exception {
        validateRequest(email, NORMAL_NAME, NORMAL_PASSWORD);
    }

    @DisplayName("Nickname을 검증한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"a", "12345678901"})
    void validateNickname(final String nickname) throws Exception {
        validateRequest(NORMAL_EMAIL, nickname, NORMAL_PASSWORD);
    }

    @DisplayName("Password를 검증한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"abc123!@#", "aaaaa11111", "aaaaa!!!!!", "11111!!!!!"})
    void validatePassword(final String password) throws Exception {
        validateRequest(NORMAL_EMAIL, NORMAL_NAME, password);
    }

    private void validateRequest(final String email, final String nickname, final String password) throws Exception {
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(String.valueOf(new CustomerRegisterRequest(email, nickname, password)))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
}
