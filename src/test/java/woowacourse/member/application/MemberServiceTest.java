package woowacourse.member.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dto.LoginRequest;
import woowacourse.member.dto.MemberInfoResponse;
import woowacourse.member.dto.SignUpRequest;
import woowacourse.member.exception.InvalidMemberEmailException;
import woowacourse.member.exception.EmailNotFoundException;
import woowacourse.member.exception.MemberNotFoundException;
import woowacourse.member.exception.WrongPasswordException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
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
        ).isInstanceOf(EmailNotFoundException.class)
                .hasMessageContaining("존재하지 않는 이메일입니다.");
    }

    @DisplayName("잘못된 비밀번호인 경우 예외가 발생한다.")
    @Test
    void verifyValidMemberWithWrongPassword() {
        assertThatThrownBy(
                () -> memberService.findIdByEmail(new LoginRequest("ari@wooteco.com","Javajigi!!"))
        ).isInstanceOf(WrongPasswordException.class)
                .hasMessageContaining("잘못된 비밀번호입니다.");
    }

    @DisplayName("올바른 id로 회원정보를 조회한다.")
    @Test
    void findMemberById() {
        MemberInfoResponse response = memberService.findMemberById(1L);

        assertAll(
                () -> assertThat(response.getEmail()).isEqualTo("ari@wooteco.com"),
                () -> assertThat(response.getName()).isEqualTo("아리")
        );
    }

    @DisplayName("존재하지 않는 id 경우 예외가 발생한다.")
    @Test
    void findMemberByIdWithNotExistId(){
        assertThatThrownBy(
                () -> memberService.findMemberById(100L)
        ).isInstanceOf(MemberNotFoundException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
    }
}
