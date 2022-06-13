package woowacourse.shoppingcart.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.shoppingcart.dto.request.CustomerRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    private static final String DEFAULT_USERNAME = "kth990303";
    private static final String DEFAULT_PASSWORD = "kth@990303";
    private static final String DEFAULT_NICKNAME = "kth990303";
    private static final int DEFAULT_AGE = 20;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("회원가입 시 아이디를 4자 미만 20자 초과로 작성한 경우 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"abc", "abcdefghijklmnopqrstu"})
    void createCustomerWithInvalidName(String userName) throws Exception {
        CustomerRequest customerRequest =
                new CustomerRequest(userName, DEFAULT_PASSWORD, DEFAULT_NICKNAME, DEFAULT_AGE);
        String message = "아이디는 4자 이상 20자 이하여야 합니다.";
        performAndExpectedErrorMessage(customerRequest, message);
    }

    @DisplayName("회원가입 시 비밀번호를 8자 미만 20자 초과로 작성한 경우 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcdefg", "abcdefghijklmnopqrstu"})
    void createCustomerWithInvalidPassword(String password) throws Exception {
        CustomerRequest customerRequest =
                new CustomerRequest(DEFAULT_PASSWORD, password, DEFAULT_NICKNAME, DEFAULT_AGE);
        String message = "비밀번호는 8자 이상 20자 이하여야 합니다.";
        performAndExpectedErrorMessage(customerRequest, message);
    }

    @DisplayName("회원가입 시 이름을 10자 초과로 작성한 경우 예외를 발생시킨다.")
    @Test
    void createCustomerWithInvalid() throws Exception {
        CustomerRequest customerRequest =
                new CustomerRequest(DEFAULT_USERNAME, DEFAULT_PASSWORD, "이런저런열한글자의이름", DEFAULT_AGE);
        String message = "이름은 1자 이상 10자 이하여야 합니다.";
        performAndExpectedErrorMessage(customerRequest, message);
    }

    @DisplayName("회원가입 시 나이를 0살 이하로 작성한 경우 예외를 발생시킨다.")
    @Test
    void createCustomerWithInvalidAge() throws Exception {
        CustomerRequest customerRequest =
                new CustomerRequest(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_NICKNAME, -1);
        String message = "올바른 나이를 입력해주세요.";
        performAndExpectedErrorMessage(customerRequest, message);
    }

    private void performAndExpectedErrorMessage(CustomerRequest customerRequest,
                                                String message) throws Exception {
        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(customerRequest))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(message));
    }
}
