package cart.intergration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.request.member.MemberSignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class MemberIntegrationTest {
    private static final String MEMBER_PATH = "/members";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("회원을 등록하고, 회원의 조회가 정상적으로 돼야 한다.")
    void saveMemberAndFindMembers() throws Exception {
        // given
        MemberSignupRequest request = new MemberSignupRequest("glen@naver.com", "123456");
        mockMvc.perform(post(MEMBER_PATH + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // expect
        mockMvc.perform(get(MEMBER_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("1명의 회원이 조회되었습니다."))
                .andExpect(jsonPath("$.result[0].email").value("glen@naver.com"))
                .andExpect(jsonPath("$.result[0].password").value("123456"));
    }

    @Test
    @DisplayName("회원을 등록하고, 같은 이메일로 등록하면 400번대 HTTP 상태 코드가 반환되어야 한다.")
    void saveMember_existsEmail() throws Exception {
        // given
        MemberSignupRequest request = new MemberSignupRequest("glen@naver.com", "123456");
        mockMvc.perform(post(MEMBER_PATH + "/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // expect
        mockMvc.perform(post(MEMBER_PATH + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.validation.email").value("이미 존재하는 이메일 입니다."));
    }

    @MethodSource("invalidEmail")
    @ParameterizedTest(name = "회원을 등록할 때 회원의 이메일이 {0} 이면 400번대 HTTP 상태 코드가 반환되어야 한다.")
    void saveMember_invalidEmail(String displayName, String email) throws Exception {
        // expect
        MemberSignupRequest request = new MemberSignupRequest(email, "123456");
        mockMvc.perform(post(MEMBER_PATH + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.validation.email").exists());
    }

    static Stream<Arguments> invalidEmail() {
        Map<String, String> testData = new HashMap<>();
        testData.put("null", null);
        testData.put("잘못된 이메일 형식", "glen");
        testData.put(":이 포함된 이메일", "gl:en@naver.com");
        testData.put("30글자가 넘는 이메일", "1234567890123456789@123456789.123");
        return testData.entrySet().stream()
                .map(data -> Arguments.of(data.getKey(), data.getValue()));
    }

    @MethodSource("invalidPassword")
    @ParameterizedTest(name = "회원을 등록할 때 회원의 비밀번호가 {0} 이면 400번대 HTTP 상태 코드가 반환되어야 한다.")
    void saveMember_invalidPassword(String displayName, String password) throws Exception {
        // expect
        MemberSignupRequest request = new MemberSignupRequest("glen@naver.com", password);
        mockMvc.perform(post(MEMBER_PATH + "/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.validation.password").exists());
    }

    static Stream<Arguments> invalidPassword() {
        Map<String, String> testData = new HashMap<>();
        testData.put("null", null);
        testData.put(":이 포함된 비밀번호", "123:456");
        testData.put("4자리 미만", "123");
        testData.put("20자리 초과", "123456789012345678901");
        return testData.entrySet().stream()
                .map(data -> Arguments.of(data.getKey(), data.getValue()));
    }
}
