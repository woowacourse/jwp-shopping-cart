package woowacourse.member.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.member.dto.SignUpRequest;
import woowacourse.member.exception.InvalidMemberEmailException;
import woowacourse.member.exception.LonginWrongEmailException;
import woowacourse.member.exception.LonginWrongPasswordException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberServiceTest {

    private final MemberService memberService;

    public MemberServiceTest(MemberService memberService) {
        this.memberService = memberService;
    }

    @DisplayName("올바른 데이터로 회원가입에 성공한다.")
    @Test
    void signUp() {
        assertDoesNotThrow(
                () -> memberService.signUp(new SignUpRequest("pobi@wooteco.com", "포비", "Javajigi!"))
        );
    }

    @DisplayName("중복된 이메일이 존재하는 경우 예외가 발생한다.")
    @Test
    void signUpWithDuplicateEmail() {
        assertThatThrownBy(
                () -> memberService.signUp(new SignUpRequest("ari@wooteco.com", "가짜아리", "Wooteco!"))
        ).isInstanceOf(InvalidMemberEmailException.class)
                .hasMessageContaining("중복되는 이메일이 존재합니다.");
    }

    @DisplayName("올바른 데이터로 로그인에 성공한다.")
    @Test
    void verifyValidMember() {
        assertDoesNotThrow(
                () -> memberService.findIdByEmail(new LoginRequest("ari@wooteco.com","Wooteco1!"))
        );
    }

    @DisplayName("존재하지 않는 이메일인 경우 예외가 발생한다.")
    @Test
    void verifyValidMemberWithNotExistEmail(){
        assertThatThrownBy(
                () -> memberService.findIdByEmail(new LoginRequest("pobi@wooteco.com","Wooteco!"))
        ).isInstanceOf(LonginWrongEmailException.class)
                .hasMessageContaining("존재하지 않는 이메일입니다.");
    }

    @DisplayName("잘못된 비밀번호인 경우 예외가 발생한다.")
    @Test
    void verifyValidMemberWithWrongPassword() {
        assertThatThrownBy(
                () -> memberService.findIdByEmail(new LoginRequest("ari@wooteco.com","Javajigi!!"))
        ).isInstanceOf(LonginWrongPasswordException.class)
                .hasMessageContaining("잘못된 비밀번호입니다.");
    }
}
