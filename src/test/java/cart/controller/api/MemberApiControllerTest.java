package cart.controller.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cart.dto.member.MemberDto;
import cart.dto.request.member.MemberSignupRequest;
import cart.exception.DuplicateEmailException;
import cart.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberApiController.class)
class MemberApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("/members/signup으로 POST 요청과 회원의 정보를 보내면 HTTP 200 코드와 함께 회원가입이 되어야 한다.")
    void signupMember_success() throws Exception {
        // given
        MemberSignupRequest request = new MemberSignupRequest("glen@naver.com", "123456");

        // expect
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원가입이 정상적으로 되었습니다."));
    }

    @Test
    @DisplayName("회원가입 시 이메일이 형식에 맞지 않으면 HTTP 400 코드와 검증 메시지가 반환되어야 한다.")
    void signupMember_invalidEmail() throws Exception {
        // given
        MemberSignupRequest request = new MemberSignupRequest("glen", "123456");
        willThrow(new DuplicateEmailException())
                .given(memberService)
                .signupMember(anyString(), anyString());

        // expect
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.email").value("올바른 이메일 형식이 아닙니다."));
    }

    @ParameterizedTest
    @EmptySource
    @NullSource
    @DisplayName("회원가입 시 이메일이 빈 값이면 HTTP 400 코드와 검증 메시지가 반환되어야 한다.")
    void signupMember_blankEmail(String email) throws Exception {
        // given
        MemberSignupRequest request = new MemberSignupRequest(email, "123456");

        // expect
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.email").value("이메일은 비어있으면 안 됩니다."));
    }

    @Test
    @DisplayName("회원가입 시 이메일의 길이가 30자리를 초과하면 HTTP 400 코드와 검증 메시지가 반환되어야 한다.")
    void signupMember_emailLengthLessThan30() throws Exception {
        // given
        String email = "0123456@89012345678901234567890"; // 31
        MemberSignupRequest request = new MemberSignupRequest(email, "123456");

        // expect
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.email").value("이메일 최대 30자리 보다 작아야 합니다."));
    }

    @Test
    @DisplayName("회원가입 시 이메일이 이미 존재하면 HTTP 400 코드와 검증 메시지가 반환되어야 한다.")
    void signupMember_duplicateEmail() throws Exception {
        // given
        MemberSignupRequest request = new MemberSignupRequest("glen@naver.com", "123456");
        willThrow(new DuplicateEmailException())
                .given(memberService)
                .signupMember(anyString(), anyString());

        // expect
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.email").value("이미 존재하는 이메일 입니다."));
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("회원가입 시 비밀번호가 빈 값이면 HTTP 400 코드와 검증 메시지가 반환되어야 한다.")
    void signupMember_blankPassword(String password) throws Exception {
        // given
        MemberSignupRequest request = new MemberSignupRequest("glen@naver.com", password);

        // expect
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.password").value("비밀번호는 비어있으면 안 됩니다."));
    }

    @ParameterizedTest
    @ValueSource(strings = {"012", "012345678901234567890"})
    @DisplayName("회원가입 시 비밀번호의 길이가 30자리를 초과하면 HTTP 400 코드와 검증 메시지가 반환되어야 한다.")
    void signupMember_invalidLengthPassword(String password) throws Exception {
        // given
        MemberSignupRequest request = new MemberSignupRequest("glen@naver.com", password);

        // expect
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.password").value("비밀번호는 4자리에서 20자리 사이여야 합니다."));
    }

    @Test
    @DisplayName("회원가입 시 이메일에 :이 포함되면 HTTP 400 코드와 검증 메시지가 반환되어야 한다.")
    void signupMember_includeColonEmail() throws Exception {
        // given
        MemberSignupRequest request = new MemberSignupRequest("glen@nav:er.com", "123456");

        // expect
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.email").value("올바른 이메일 형식이 아닙니다."));
    }

    @Test
    @DisplayName("회원가입 시 비밀번호에 :이 포함되면 HTTP 400 코드와 검증 메시지가 반환되어야 한다.")
    void signupMember_includeColonPassword() throws Exception {
        // given
        MemberSignupRequest request = new MemberSignupRequest("glen@naver.com", "123:456");

        // expect
        mockMvc.perform(post("/members/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.password").value("\":\"가 포함될 수 없습니다."));
    }

    @Test
    @DisplayName("/members로 GET 요청을 보내면 HTTP 200 코드와 함께 모든 회원이 조회되어야 한다.")
    void findAllMembers_success() throws Exception {
        // given
        List<MemberDto> memberDtos = List.of(
                new MemberDto(1L, "glenfiddch@naver.com", "123456"),
                new MemberDto(2L, "glenlivet@naver.com", "123456"),
                new MemberDto(3L, "glendronach@naver.com", "123456")
        );

        willReturn(memberDtos)
                .given(memberService)
                .findAllMembers();

        // expect
        mockMvc.perform(get("/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("3명의 회원이 조회되었습니다."))
                .andExpect(jsonPath("$.result[0].id").value(1))
                .andExpect(jsonPath("$.result[0].email").value("glenfiddch@naver.com"))
                .andExpect(jsonPath("$.result[0].password").value("123456"))

                .andExpect(jsonPath("$.result[1].id").value(2))
                .andExpect(jsonPath("$.result[1].email").value("glenlivet@naver.com"))
                .andExpect(jsonPath("$.result[1].password").value("123456"))

                .andExpect(jsonPath("$.result[2].id").value(3))
                .andExpect(jsonPath("$.result[2].email").value("glendronach@naver.com"))
                .andExpect(jsonPath("$.result[2].password").value("123456"));
    }
}
