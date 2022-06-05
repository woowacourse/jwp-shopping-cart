package woowacourse.member.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.dto.request.LoginRequest;
import woowacourse.member.dto.request.*;
import woowacourse.member.dto.response.MemberInfoResponse;
import woowacourse.member.exception.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("MemberService에서")
@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class MemberServiceTest {

    private final MemberService memberService;

    public MemberServiceTest(MemberService memberService) {
        this.memberService = memberService;
    }

    @DisplayName("login 메서드는")
    @Nested
    class loginTest {

        @DisplayName("올바른 데이터인 경우 id값을 반환한다.")
        @Test
        void logIn() {
            long id = memberService.logIn(new LoginRequest("ari@wooteco.com", "Wooteco1!"));
            assertThat(id).isEqualTo(1L);
        }

        @DisplayName("존재하지 않는 이메일인 경우 예외가 발생한다.")
        @Test
        void logInWithNotExistEmail() {
            assertThatThrownBy(
                    () -> memberService.logIn(new LoginRequest("pobi@wooteco.com", "Wooteco!"))
            ).isInstanceOf(MemberNotFoundException.class)
                    .hasMessageContaining("해당 이메일로 가입된 회원은 존재하지 않습니다.");
        }

        @DisplayName("잘못된 비밀번호인 경우 예외가 발생한다.")
        @Test
        void logInWithWrongPassword() {
            assertThatThrownBy(
                    () -> memberService.logIn(new LoginRequest("ari@wooteco.com", "Javajigi!!"))
            ).isInstanceOf(WrongPasswordException.class)
                    .hasMessageContaining("잘못된 비밀번호입니다.");
        }
    }

    @DisplayName("signUp 메서드는")
    @Nested
    class signUpTest {

        @DisplayName("올바른 데이터인 경우 회원가입에 성공한다.")
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
            ).isInstanceOf(DuplicateEmailException.class)
                    .hasMessageContaining("중복되는 이메일이 존재합니다.");
        }
    }

    @DisplayName("중복 이메일 확인 메서드는")
    @Nested
    class checkDuplicateEmailTest {

        @DisplayName("존재하지 않는 이메일인 경우 예외가 발생하지 않는다.")
        @Test
        void checkDuplicateEmailWithNotDuplicateEmail() {
            assertDoesNotThrow(
                    () -> memberService.checkDuplicateEmail(new DuplicateEmailRequest("pobi@wooteco.com"))
            );
        }

        @DisplayName("이미 존재하는 이메일인 경우 예외가 발생한다.")
        @Test
        void checkDuplicateEmailWithDuplicateEmail() {
            assertThatThrownBy(
                    () -> memberService.checkDuplicateEmail(new DuplicateEmailRequest("ari@wooteco.com"))
            ).isInstanceOf(DuplicateEmailException.class)
                    .hasMessageContaining("중복되는 이메일이 존재합니다.");
        }
    }

    @DisplayName("findMemberInfoById 메서드는")
    @Nested
    class findMemberInfoByIdTest {

        @DisplayName("Id로 회원정보를 조회한다.")
        @Test
        void findMemberById() {
            MemberInfoResponse response = memberService.findMemberInfoById(1L);

            assertAll(
                    () -> assertThat(response.getEmail()).isEqualTo("ari@wooteco.com"),
                    () -> assertThat(response.getName()).isEqualTo("아리")
            );
        }

        @DisplayName("존재하지 않는 id인 경우 예외가 발생한다.")
        @Test
        void findMemberByIdWithNotExistId() {
            assertThatThrownBy(
                    () -> memberService.findMemberInfoById(100L)
            ).isInstanceOf(MemberNotFoundException.class)
                    .hasMessageContaining("존재하지 않는 회원입니다.");
        }
    }

    @DisplayName("updateName 메서드는")
    @Nested
    class updateNameTest {

        @DisplayName("id로 해당 회원 이름을 변경한다.")
        @Test
        void updateName() {
            memberService.updateName(1L, new UpdateNameRequest("메아리"));
            assertThat(memberService.findMemberInfoById(1L).getName()).isEqualTo("메아리");
        }

        @DisplayName("현재이름과 같은 이름인 경우 예외가 발생한다.")
        @Test
        void updateNameWithSameName() {
            assertThatThrownBy(
                    () -> memberService.updateName(1L, new UpdateNameRequest("아리"))
            ).isInstanceOf(InvalidMemberNameException.class)
                    .hasMessageContaining("현재 이름과 같은 이름으로 변경할 수 없습니다.");
        }
    }

    @DisplayName("updatePassword 메서드는")
    @Nested
    class updatePasswordTest {

        @DisplayName("id로 해당 회원 비밀번호를 변경한다.")
        @Test
        void updatePassword() {
            memberService.updatePassword(1L, new UpdatePasswordRequest("Wooteco1!", "NewPassword1!"));
            assertThat(memberService.logIn(new LoginRequest("ari@wooteco.com", "NewPassword1!"))).isEqualTo(1L);
        }

        @DisplayName("현재 비밀번호와 일치하지 않는 경우 예외가 발생한다.")
        @Test
        void updatePasswordWithIncorrectPassword() {
            assertThatThrownBy(
                    () -> memberService.updatePassword(1L, new UpdatePasswordRequest("wrongPassword!", "NewPassword1"))
            ).isInstanceOf(InvalidPasswordException.class)
                    .hasMessageContaining("현재 비밀번호와 일치하지 않습니다.");
        }

        @DisplayName("현재 비밀번호와 같은 비밀번호인 경우 예외가 발생한다.")
        @Test
        void updatePasswordWithSamePassword() {
            assertThatThrownBy(
                    () -> memberService.updatePassword(1L, new UpdatePasswordRequest("Wooteco1!", "Wooteco1!"))
            ).isInstanceOf(InvalidPasswordException.class)
                    .hasMessageContaining("현재 비밀번호와 같은 비밀번호로 변경할 수 없습니다.");
        }
    }

    @DisplayName("deleteMemberById 메서드는")
    @Nested
    class deleteMemberByIdTest {

        @DisplayName("id로 해당 회원 정보를 삭제한다.")
        @Test
        void deleteById() {
            memberService.deleteMemberById(1L);

            assertThatThrownBy(
                    () -> memberService.findMemberInfoById(1L)
            ).isInstanceOf(MemberNotFoundException.class)
                    .hasMessageContaining("존재하지 않는 회원입니다.");
        }

        @DisplayName("존재하지 않는 id인 경우 예외가 발생한다.")
        @Test
        void deleteWithNotExistId() {
            assertThatThrownBy(
                    () -> memberService.deleteMemberById(100L)
            ).isInstanceOf(MemberNotFoundException.class)
                    .hasMessageContaining("존재하지 않는 회원입니다.");
        }

    }

}
