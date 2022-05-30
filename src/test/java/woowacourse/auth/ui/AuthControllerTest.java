package woowacourse.auth.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import woowacourse.auth.dto.LoginRequest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("이메일은 빈 값을 허용하지 않는다.")
    @Test
    void signUpWithBlankEmail() throws Exception {
        LoginRequest request = new LoginRequest("", "Woowacourse1!");
        mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("이메일은 빈 값일 수 없습니다.")));
    }

    @DisplayName("올바르지 못한 형식의 이메일은 허용하지 않는다.")
    @Test
    void signUpWithInvalidForm() throws Exception {
        LoginRequest request = new LoginRequest("woowacourse12naver.com", "Woowacourse1!");
        mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("올바르지 않은 형식의 이메일입니다.")));
    }

    @DisplayName("비밀번호는 Null 값은 허용하지 않는다.")
    @Test
    void signUpWithNullPassword() throws Exception {
        LoginRequest request = new LoginRequest("woowacourse12@naver.com", null);
        mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("비밀번호는 빈 값일 수 없습니다.")));
    }

    @DisplayName("비밀번호는 빈 값을 허용하지 않는다.")
    @Test
    void signUpWithBlankPassword() throws Exception {
        LoginRequest request = new LoginRequest("woowacourse12@naver.com", "");
        mockMvc.perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("비밀번호는 빈 값일 수 없습니다.")));
    }
}
