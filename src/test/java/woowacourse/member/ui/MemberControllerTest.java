package woowacourse.member.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.member.dto.request.LoginRequest;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.member.dto.request.*;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("MemberController에서")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("login 메서드는")
    @Nested
    class LoginTest {
        private final String uri = "/api/auth";

        @DisplayName("이메일에 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyEmail(String email) throws Exception {
            LoginRequest request = new LoginRequest(email, "Woowacourse1!");
            assertBadRequestFromPost(uri, request, "이메일은 빈 값일 수 없습니다.");
        }

        @DisplayName("올바르지 못한 형식의 이메일은 허용하지 않는다.")
        @Test
        void invalidEmailForm() throws Exception {
            LoginRequest request = new LoginRequest("woowacourse12naver.com", "Woowacourse1!");
            assertBadRequestFromPost(uri, request, "올바르지 않은 형식의 이메일입니다.");
        }

        @DisplayName("비밀번호 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyPassword(String password) throws Exception {
            LoginRequest request = new LoginRequest("woowacourse12@naver.com", password);
            assertBadRequestFromPost(uri, request, "비밀번호는 빈 값일 수 없습니다.");
        }
    }

    @DisplayName("signUp 메서드는")
    @Nested
    class SignUpTest {
        private final String uri = "/api/members";

        @DisplayName("이름에 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyName(String name) throws Exception {
            SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", name, "Woowacourse1!");
            assertBadRequestFromPost(uri, request, "이름은 빈 값일 수 없습니다.");
        }

        @DisplayName("이메일에 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyEmail(String email) throws Exception {
            SignUpRequest request = new SignUpRequest(email, "우테코", "Woowacourse1!");
            assertBadRequestFromPost(uri, request, "이메일은 빈 값일 수 없습니다.");
        }

        @DisplayName("올바르지 못한 형식의 이메일은 허용하지 않는다.")
        @Test
        void invalidEmailForm() throws Exception {
            SignUpRequest request = new SignUpRequest("woowacourse12naver.com", "우테코", "Woowacourse1!");
            assertBadRequestFromPost(uri, request, "올바르지 않은 형식의 이메일입니다.");
        }

        @DisplayName("비밀번호에 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyPassword(String password) throws Exception {
            SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", "우테코", password);
            assertBadRequestFromPost(uri, request, "비밀번호는 빈 값일 수 없습니다.");
        }
    }

    @DisplayName("checkDuplicateEmail 메서드는")
    @Nested
    class CheckDuplicateEmailTest {
        private final String uri = "/api/members/duplicate-email?email=";

        @DisplayName("이메일에 Null 값, 빈 값을 허용하지 않는다.")
        @Test
        void emptyEmail() throws Exception {
            assertBadRequestFromGet(uri, "이메일은 빈 값일 수 없습니다.");
        }

        @DisplayName("올바르지 못한 형식의 이메일은 허용하지 않는다.")
        @Test
        void invalidEmailForm() throws Exception {
            assertBadRequestFromGet(uri + "woowacourse12naver.com", "올바르지 않은 형식의 이메일입니다.");
        }
    }

    @DisplayName("updateName 메서드는")
    @Nested
    class UpdateNameTest {
        private final String uri = "/api/members/me/name";

        @DisplayName("이름 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyName(String name) throws Exception {
            UpdateNameRequest request = new UpdateNameRequest(name);
            assertBadRequestFromPut(uri, request, "이름은 빈 값일 수 없습니다.");
        }

    }

    @DisplayName("updatePassword 메서드는")
    @Nested
    class UpdatePasswordTest {
        private final String uri = "/api/members/me/password";

        @DisplayName("이전 비밀번호 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyOldPassword(String oldPassword) throws Exception {
            UpdatePasswordRequest request = new UpdatePasswordRequest(oldPassword, "Wooteco1!");
            assertBadRequestFromPut(uri, request, "비밀번호는 빈 값일 수 없습니다.");
        }

        @DisplayName("이전 비밀번호 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyNewPassword(String newPassword) throws Exception {
            UpdatePasswordRequest request = new UpdatePasswordRequest("Wooteco1!", newPassword);
            assertBadRequestFromPut(uri, request, "비밀번호는 빈 값일 수 없습니다.");
        }
    }

    void assertBadRequestFromGet(String uri, String errorMessage) throws Exception {
        mockMvc.perform(get(uri))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }

    void assertBadRequestFromPost(String uri, Object request, String errorMessage) throws Exception {
        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }

    void assertBadRequestFromPut(String uri, Object request, String errorMessage) throws Exception {
        String token = jwtTokenProvider.createToken("1");

        mockMvc.perform(put(uri)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }
}
