package woowacourse.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.dto.request.PasswordRequest;
import woowacourse.member.exception.AuthorizationException;
import woowacourse.member.dto.request.LoginRequest;
import woowacourse.member.dto.request.MemberCreateRequest;
import woowacourse.member.dto.request.MemberUpdateRequest;
import woowacourse.member.dto.response.LoginResponse;
import woowacourse.member.dto.response.MemberResponse;

@SpringBootTest
@Sql("file:src/test/resources/test_member.sql")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class MemberServiceTest {

    private static final String EMAIL = "abc@woowahan.com";
    private static final long MEMBER_ID = 1L;
    private static final String PASSWORD = "1q2w3e4r!";
    private static final String NICKNAME = "닉네임";
    private static final long NON_EXISTING_MEMBER_ID = 2L;
    private static final String NEW_PASSWORD = "1q2w3e4r@";
    private static final String NEW_NICKNAME = "바뀐닉네임";

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService.save(new MemberCreateRequest(EMAIL, PASSWORD, NICKNAME));
    }

    @DisplayName("이미 존재하는 이메일로 회원을 생성하려고 하면 예외를 반환한다.")
    @Test
    void saveMember_DuplicatedEmail() {
        assertThatThrownBy(() -> memberService.save(new MemberCreateRequest(EMAIL, PASSWORD, NICKNAME)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 존재하는 이메일 주소입니다.");
    }

    @DisplayName("로그인에 성공하면 jwt 토큰과 닉네임을 반환한다.")
    @Test
    void login() {
        LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);

        LoginResponse loginResponse = memberService.login(loginRequest);

        assertAll(
                () -> assertThat(loginResponse.getToken()).isNotNull(),
                () -> assertThat(loginResponse.getNickname()).isEqualTo(NICKNAME)
        );
    }

    @DisplayName("올바르지 않은 정보로 로그인하려고 하면 예외를 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@naver.com, 1q2w3e4r!", "abc@woowahan.com, asdas1123!", "abc@naver.com, asdas1123!"})
    void login_Invalid(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);

        assertThatThrownBy(() -> memberService.login(loginRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일과 비밀번호를 확인해주세요.");
    }

    @DisplayName("토큰과 비밀번호를 받아서, 올바른지 반환한다.")
    @ParameterizedTest
    @CsvSource({"1q2w3e4r!, true", "asda1234!, false"})
    void checkPassword(String password, boolean expected) {
        boolean actual = memberService.checkPassword(MEMBER_ID, new PasswordRequest(password))
                .isSuccess();

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("이메일과 수정할 회원 정보를 받아 회원 정보를 수정한다.")
    @Test
    void updateMember() {
        memberService.updateMember(MEMBER_ID, new MemberUpdateRequest(NEW_NICKNAME));
        LoginRequest loginRequest = new LoginRequest(EMAIL, PASSWORD);

        String updatedNickname = memberService.login(loginRequest)
                .getNickname();

        assertThat(updatedNickname).isEqualTo(NEW_NICKNAME);
    }

    @DisplayName("존재하지 않는 회원의 정보를 수정하려고 하면 예외를 반환한다.")
    @Test
    void updateMember_NotFoundMember() {
        assertThatThrownBy(
                () -> memberService.updateMember(NON_EXISTING_MEMBER_ID, new MemberUpdateRequest(NEW_NICKNAME)))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }

    @DisplayName("올바르지 않은 형식의 닉네임으로 변경하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_InvalidNicknameFormat() {
        String invalidNickname = "1234";

        assertThatThrownBy(() -> memberService.updateMember(MEMBER_ID, new MemberUpdateRequest(invalidNickname)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 형식이 올바르지 않습니다.");
    }

    @DisplayName("이메일과 비밀번호를 받아 비밀번호를 수정한다.")
    @Test
    void updatePassword() {
        memberService.updatePassword(MEMBER_ID, new PasswordRequest(NEW_PASSWORD));

        LoginRequest loginRequest = new LoginRequest(EMAIL, NEW_PASSWORD);

        assertThatCode(() -> memberService.login(loginRequest))
                .doesNotThrowAnyException();
    }

    @DisplayName("회원의 정보를 반환한다.")
    @Test
    void findMember() {
        MemberResponse memberResponse = memberService.findMember(MEMBER_ID);

        assertAll(
                () -> assertThat(memberResponse.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(memberResponse.getNickname()).isEqualTo(NICKNAME)
        );
    }

    @DisplayName("존재하지 않는 회원의 비밀번호를 수정하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_NotFoundMember() {
        assertThatThrownBy(
                () -> memberService.updatePassword(NON_EXISTING_MEMBER_ID, new PasswordRequest(NEW_PASSWORD)))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }

    @DisplayName("존재하지 않는 회원의 정보를 요청하면 예외를 반환한다.")
    @Test
    void findMember_NotFoundMember() {
        assertThatThrownBy(() -> memberService.findMember(NON_EXISTING_MEMBER_ID))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }

    @DisplayName("올바르지 않은 형식의 비밀번호로 변경하려고 하면 예외를 반환한다.")
    @Test
    void updatePassword_InvalidPasswordFormat() {
        PasswordRequest invalidPasswordRequest = new PasswordRequest("1234");

        assertThatThrownBy(() -> memberService.updatePassword(MEMBER_ID, invalidPasswordRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("이메일이 일치하는 회원 정보를 삭제한다.")
    @Test
    void deleteMember() {
        memberService.deleteMember(MEMBER_ID);

        boolean actual = memberService.checkUniqueEmail(EMAIL)
                .isUnique();

        assertThat(actual).isTrue();
    }

    @DisplayName("존재하지 않는 이메일인지 반환한다.")
    @ParameterizedTest
    @CsvSource({"abc@woowahan.com, false", "abc@naver.com, true"})
    void existsEmail(String email, boolean expected) {
        boolean actual = memberService.checkUniqueEmail(email)
                .isUnique();

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("올바르지 않은 이메일 형식으로 이메일이 존재하는지 확인하려하면 예외를 반환한다.")
    @Test
    void existsEmail_InvalidFormat() {
        String invalidEmail = "abc";

        assertThatThrownBy(() -> memberService.checkUniqueEmail(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }

    @DisplayName("존재하지 않는 회원을 삭제하려 하면 예외를 반환한다.")
    @Test
    void deleteMember_NotFoundMember() {
        assertThatThrownBy(() -> memberService.deleteMember(NON_EXISTING_MEMBER_ID))
                .isInstanceOf(AuthorizationException.class)
                .hasMessage("유효하지 않은 토큰입니다.");
    }
}
