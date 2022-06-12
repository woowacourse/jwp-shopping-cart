package woowacourse.member.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.member.ui.dto.SignUpRequest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("로그인 테스트")
    @Nested
    class SignUpTest {

        @DisplayName("이름은 Null 값은 허용하지 않는다.")
        @Test
        void signUpWithNullName() throws Exception {
            SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", null, "Woowacourse1!");
            mockMvc.perform(post("/api/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("이름은 빈 값일 수 없습니다.")));
        }

        @DisplayName("이름은 빈 값을 허용하지 않는다.")
        @Test
        void signUpWithBlankName() throws Exception {
            SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", "", "Woowacourse1!");
            mockMvc.perform(post("/api/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("이름은 빈 값일 수 없습니다.")));
        }

        @DisplayName("이메일은 Null 값은 허용하지 않는다.")
        @Test
        void signUpWithNullEmail() throws Exception {
            SignUpRequest request = new SignUpRequest(null, "우테코", "Woowacourse1!");
            mockMvc.perform(post("/api/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("이메일은 빈 값일 수 없습니다.")));
        }

        @DisplayName("이메일은 빈 값을 허용하지 않는다.")
        @Test
        void signUpWithBlankEmail() throws Exception {
            SignUpRequest request = new SignUpRequest("", "우테코", "Woowacourse1!");
            mockMvc.perform(post("/api/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("이메일은 빈 값일 수 없습니다.")));
        }

        @DisplayName("올바르지 못한 형식의 이메일은 허용하지 않는다.")
        @Test
        void signUpWithInvalidForm() throws Exception {
            SignUpRequest request = new SignUpRequest("woowacourse12naver.com", "우테코", "Woowacourse1!");
            mockMvc.perform(post("/api/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("올바르지 않은 형식의 이메일입니다.")));
        }

        @DisplayName("비밀번호는 Null 값은 허용하지 않는다.")
        @Test
        void signUpWithNullPassword() throws Exception {
            SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", "우테코", null);
            mockMvc.perform(post("/api/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("비밀번호는 빈 값일 수 없습니다.")));
        }

        @DisplayName("비밀번호는 빈 값을 허용하지 않는다.")
        @Test
        void signUpWithBlankPassword() throws Exception {
            SignUpRequest request = new SignUpRequest("woowacourse12@naver.com", "우테코", "");
            mockMvc.perform(post("/api/members")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    ).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("비밀번호는 빈 값일 수 없습니다.")));
        }
    }
}
