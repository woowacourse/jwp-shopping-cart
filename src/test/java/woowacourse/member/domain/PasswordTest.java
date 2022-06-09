package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.member.domain.password.EncryptedPassword;
import woowacourse.member.domain.password.UnencryptedPassword;
import woowacourse.member.domain.password.Password;
import woowacourse.member.exception.InvalidPasswordException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordTest {

    @DisplayName("비밀번호는 6글자 이상이어야 한다.")
    @Test
    void lessThenSixLetters() {
        assertThatThrownBy(() -> new UnencryptedPassword("Wtc1!"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("비밀번호는 6글자 이상이어야 합니다.");
    }

    @DisplayName("비밀번호는 대소문자를 포함해야 한다.")
    @Test
    void containsCase() {
        assertThatThrownBy(() -> new UnencryptedPassword("wooteco!"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("비밀번호는 대소문자를 포함해야 합니다.");
    }

    @DisplayName("비밀번호는 특수문자(!,@,?,-)를 포함해야 한다.")
    @Test
    void containsSpecialCharacters() {
        assertThatThrownBy(() -> new UnencryptedPassword("Wooteco"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("비밀번호는 특수문자(!,@,?,-)를 포함해야 합니다");
    }

    @DisplayName("객체 생성시 비밀번호를 암호화한다.")
    @Test
    void encryptPassword() {
        Password password = new UnencryptedPassword("Wooteco123!");
        assertThat(password.getValue()).isNotEqualTo("Wooteco123");
    }

    @DisplayName("같은 비밀번호로 만들어진 경우 동일하다고 판단한다.")
    @Test
    void equals() {
        Password password = new UnencryptedPassword("Wooteco123!");
        Password comparison = new UnencryptedPassword("Wooteco123!");
        boolean result = password.equals(comparison);
        assertThat(result).isTrue();
    }

    @DisplayName("다른 타입이어도 같은 값의 비밀번호면 동등하다고 판단한다.")
    @Test
    void equals2() {
        Password password = new UnencryptedPassword("Wooteco123!");
        Password comparison = new EncryptedPassword(password.getValue());
        boolean result = password.equals(comparison);
        assertThat(result).isTrue();
    }

    @DisplayName("다른 비밀번호로 만들어진 경우 동일하다고 판단한다.")
    @Test
    void notEquals() {
        Password password = new UnencryptedPassword("Wooteco123!");
        Password comparison = new UnencryptedPassword("Wooteco123?");
        boolean result = password.equals(comparison);
        assertThat(result).isFalse();
    }
}
