package woowacourse.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.ENCODE_PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;
import static woowacourse.helper.fixture.MemberFixture.passwordEncoder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.member.exception.EmailNotValidException;
import woowacourse.member.exception.NameNotValidException;
import woowacourse.member.exception.PasswordChangeException;
import woowacourse.member.exception.PasswordNotValidException;
import woowacourse.member.exception.WrongPasswordException;

public class MemberTest {

    @DisplayName("비밀번호를 암호화한다.")
    @Test
    void encodePassword() {
        Member member = createMember(EMAIL, PASSWORD, NAME);

        assertThat(member.getPassword()).isEqualTo(ENCODE_PASSWORD);
    }

    @DisplayName("비밀번호를 확인한다.")
    @Test
    void validateWrongPassword() {
        Member member = createMember(EMAIL, PASSWORD, NAME);

        assertThatNoException().isThrownBy(() -> member.validateWrongPassword(PASSWORD, passwordEncoder()));
    }

    @DisplayName("이름을 변경한다.")
    @Test
    void updateName() {
        Member member = createMember(EMAIL, PASSWORD, NAME);
        member.updateName("MARU");

        assertThat(member.getName()).isEqualTo("MARU");
    }

    @DisplayName("이름 변경시 올바른 형식이 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "aaaaaaaaaaa"})
    void updateNameException(String value) {
        Member member = createMember(EMAIL, PASSWORD, NAME);
        assertThatThrownBy(() -> member.updateName(value))
                .isInstanceOf(NameNotValidException.class);
    }

    @DisplayName("이메일 형식이 아니면 에러가 발생한다.")
    @Test
    void validateRightEmail() {
        assertThatThrownBy(() -> createMember("abcde", PASSWORD, NAME))
                .isInstanceOf(EmailNotValidException.class);
    }

    @DisplayName("비밀번호 형식이 아니면 에러가 발생한다.")
    @Test
    void validateRightPassword() {
        assertThatThrownBy(() -> createMember(EMAIL, "1234", NAME))
                .isInstanceOf(PasswordNotValidException.class);
    }

    @DisplayName("올바른 이름 형식이 아니면 에러가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "aaaaaaaaaaa"})
    void validateRightName(String value) {
        assertThatThrownBy(() -> createMember(EMAIL, PASSWORD, value))
                .isInstanceOf(NameNotValidException.class);
    }

    @DisplayName("비밀번호를 변경한다.")
    @Test
    void updatePassword() {
        Member member = createMember(EMAIL, PASSWORD, NAME);
        String originPassword = member.getPassword();

        member.updatePassword(PASSWORD, "Maru1234!", passwordEncoder());
        String updatedPassword = member.getPassword();

        assertThat(originPassword).isNotEqualTo(updatedPassword);
    }

    @DisplayName("비밀번호 변경시 이전 비밀번호와 다르면 예외가 발생한다.")
    @Test
    void updatePasswordNotSameOriginPassword() {
        Member member = createMember(EMAIL, PASSWORD, NAME);

        assertThatThrownBy(() ->
                member.updatePassword("Wrong1!", "Maru1234!", passwordEncoder()))
                .isInstanceOf(WrongPasswordException.class);
    }

    @DisplayName("비밀번호 변경시 변경할 비밀번호가 조건에 맞지 않으면 예외가 발생한다.")
    @Test
    void updatePasswordNotRight() {
        Member member = createMember(EMAIL, PASSWORD, NAME);

        assertThatThrownBy(() ->
                member.updatePassword(PASSWORD, "1!", passwordEncoder()))
                .isInstanceOf(PasswordNotValidException.class);
    }

    @DisplayName("비밀번호 변경시 변경할 비밀번호와 과거 비밀번호가 같으면 예외가 발생한다.")
    @Test
    void updatePasswordSameWithOld() {
        Member member = createMember(EMAIL, PASSWORD, NAME);

        assertThatThrownBy(() ->
                member.updatePassword(PASSWORD, PASSWORD, passwordEncoder()))
                .isInstanceOf(PasswordChangeException.class);
    }
}
