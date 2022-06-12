package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.member.domain.password.Password;
import woowacourse.member.domain.password.PlainPassword;
import woowacourse.member.exception.InvalidPasswordException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PlainPasswordTest {

    @DisplayName("비밀번호는 6글자 이상이어야 한다.")
    @Test
    void lessThenSixLetters() {
        assertThatThrownBy(() -> new PlainPassword("Wtc1!"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("비밀번호는 6글자 이상이어야 합니다.");
    }

    @DisplayName("비밀번호는 대소문자를 포함해야 한다.")
    @Test
    void containsCase() {
        assertThatThrownBy(() -> new PlainPassword("wooteco!"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("비밀번호는 대소문자를 포함해야 합니다.");
    }

    @DisplayName("비밀번호는 특수문자(!,@,?,-)를 포함해야 한다.")
    @Test
    void containsSpecialCharacters() {
        assertThatThrownBy(() -> new PlainPassword("Wooteco"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("비밀번호는 특수문자(!,@,?,-)를 포함해야 합니다");
    }

    @DisplayName("암호화된 Password 객체 생성시 비밀번호를 암호화한다.")
    @Test
    void encryptPassword() {
        PlainPassword plainPassword = new PlainPassword("Wooteco123!");
        Password password = plainPassword.encrypt();
        assertThat(password.getValue()).isNotEqualTo("Wooteco123");
    }
}
