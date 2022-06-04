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
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("로그인 테스트")
    @Nested
    class LoginTest {
        private final String uri = "/api/auth";

        @DisplayName("이메일은 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyEmail(String email) throws Exception {
            LoginRequest request = new LoginRequest(email, "Woowacourse1!");
            testPostBadRequest(uri, request, "이메일은 빈 값일 수 없습니다.");
        }

        @DisplayName("올바르지 못한 형식의 이메일은 허용하지 않는다.")
        @Test
        void invalidEmailForm() throws Exception {
            LoginRequest request = new LoginRequest("woowacourse12naver.com", "Woowacourse1!");
            testPostBadRequest(uri, request, "올바르지 않은 형식의 이메일입니다.");
        }

        @DisplayName("비밀번호는 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyPassword(String password) throws Exception {
            LoginRequest request = new LoginRequest("woowacourse12@naver.com", password);
            testPostBadRequest(uri, request, "비밀번호는 빈 값일 수 없습니다.");
        }
    }

    @DisplayName("회원가입 테스트")
    @Nested
    class SignUpTest {
        private final String uri = "/api/members";

        @DisplayName("이름은 Null Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyName(String name) throws Exception {
            SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", name, "Woowacourse1!");
            testPostBadRequest(uri, request, "이름은 빈 값일 수 없습니다.");
        }

        @DisplayName("이메일은 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyEmail(String email) throws Exception {
            SignUpRequest request = new SignUpRequest(email, "우테코", "Woowacourse1!");
            testPostBadRequest(uri, request, "이메일은 빈 값일 수 없습니다.");
        }

        @DisplayName("올바르지 못한 형식의 이메일은 허용하지 않는다.")
        @Test
        void invalidEmailForm() throws Exception {
            SignUpRequest request = new SignUpRequest("woowacourse12naver.com", "우테코", "Woowacourse1!");
            testPostBadRequest(uri, request, "올바르지 않은 형식의 이메일입니다.");
        }

        @DisplayName("비밀번호는 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyPassword(String password) throws Exception {
            SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", "우테코", password);
            testPostBadRequest(uri, request, "비밀번호는 빈 값일 수 없습니다.");
        }
    }

    @DisplayName("이메일 중복 검증 테스트")
    @Nested
    class CheckDuplicateEmailTest {
        private final String uri = "/api/members/duplicate-email";

        @DisplayName("이메일은 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyEmail(String email) throws Exception {
            DuplicateEmailRequest request = new DuplicateEmailRequest(email);
            testPostBadRequest(uri, request, "이메일은 빈 값일 수 없습니다.");
        }

        @DisplayName("올바르지 못한 형식의 이메일은 허용하지 않는다.")
        @Test
        void invalidEmailForm() throws Exception {
            DuplicateEmailRequest request = new DuplicateEmailRequest("wooteco.com");
            testPostBadRequest(uri, request, "올바르지 않은 형식의 이메일입니다.");
        }
    }

    @DisplayName("이름 수정 테스트")
    @Nested
    class UpdateNameTest {
        private final String uri = "/api/members/me/name";

        @DisplayName("이름은 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyName(String name) throws Exception {
            UpdateNameRequest request = new UpdateNameRequest(name);
            testPutBadRequest(uri, request, "이름은 빈 값일 수 없습니다.");
        }

    }

    @DisplayName("비밀번호 수정 테스트")
    @Nested
    class UpdatePasswordTest {
        private final String uri = "/api/members/me/password";

        @DisplayName("비밀번호는 Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyName(String oldPassword, String newPassword) throws Exception {
            UpdatePasswordRequest request = new UpdatePasswordRequest(oldPassword, newPassword);
            testPutBadRequest(uri, request, "비밀번호는 빈 값일 수 없습니다.");
        }
    }

    @DisplayName("회원탈퇴 테스트")
    @Nested
    class DeleteMemberTest {
        private final String uri = "/api/members/me";

        @DisplayName("비밀번호는  Null 값, 빈 값을 허용하지 않는다.")
        @ParameterizedTest
        @NullAndEmptySource
        void emptyPassword(String password) throws Exception {
            DeleteMemberRequest request = new DeleteMemberRequest(password);
            testDeleteBadRequest(uri, request, "비밀번호는 빈 값일 수 없습니다.");
        }

    }

    void testPostBadRequest(String uri, Object request, String errorMessage) throws Exception {
        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }

    void testPutBadRequest(String uri, Object request, String errorMessage) throws Exception {
        String token = jwtTokenProvider.createToken("1");

        mockMvc.perform(put(uri)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }

    void testDeleteBadRequest(String uri, Object request, String errorMessage) throws Exception {
        String token = jwtTokenProvider.createToken("1");

        mockMvc.perform(delete(uri)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString(errorMessage)));
    }
}
