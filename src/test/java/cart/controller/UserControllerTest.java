package cart.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.controller.dto.UserSaveRequest;
import cart.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @DisplayName("POST /users 요청 시")
    @Nested
    class postUsers {

        @DisplayName("입력이 올바른 경우 Status OK를 반환한다.")
        @Test
        void shouldResponseStatusOkWhenRequestPostToUsers() throws Exception {
            UserSaveRequest request = new UserSaveRequest(
                    "email@test.test",
                    "1234abcd!@",
                    "Test Name",
                    "01012341234"
            );
            String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isOk());
        }

        @DisplayName("이메일이 비어있는 경우 Status Bad Request를 반환한다.")
        @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
        @ValueSource(strings = {" ", "  "})
        @NullAndEmptySource
        void shouldResponseStatusBadRequestWhenEmailIsNullOrBlank(String inputEmail) throws Exception {
            UserSaveRequest request = new UserSaveRequest(
                    inputEmail,
                    "1234abcd!@",
                    "Test Name",
                    "01012341234"
            );
            String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("비밀번호가 비어있는 경우 Status Bad Request를 반환한다.")
        @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
        @ValueSource(strings = {" ", "  "})
        @NullAndEmptySource
        void shouldResponseStatusBadRequestWhenPasswordIsNullOrBlank(String inputPassword) throws Exception {
            UserSaveRequest request = new UserSaveRequest(
                    "email@test.test",
                    inputPassword,
                    "Test Name",
                    "01012341234"
            );
            String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }

        @DisplayName("비밀번호가 규칙에 맞지 않는 경우 Status Bad Request를 반환한다.")
        @ParameterizedTest(name = "잘못된 값 (\"{0}\")")
        @ValueSource(strings = {"123456789a", "abcdefghi!", "123456789!", "abc123!@#"})
        void shouldResponseStatusBadRequestWhenPasswordDoesNotMatch(String inputPassword) throws Exception {
            UserSaveRequest request = new UserSaveRequest(
                    "email@test.test",
                    inputPassword,
                    "Test Name",
                    "01012341234"
            );
            String requestJson = objectMapper.writeValueAsString(request);

            mockMvc.perform(post("/users")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(status().isBadRequest());
        }
    }
}
