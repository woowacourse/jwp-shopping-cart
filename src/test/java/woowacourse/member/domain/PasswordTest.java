package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.member.exception.InvalidPasswordException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordTest {

    @DisplayName("비밀번호는 6글자 이상이어야 한다.")
    @Test
    void lessThenSixLetters() {
        assertThatThrownBy(() -> Password.withEncrypt("Wtc1!"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("비밀번호는 6글자 이상이어야 합니다.");
    }

    @DisplayName("비밀번호는 대소문자를 포함해야 한다.")
    @Test
    void containsCase() {
        assertThatThrownBy(() -> Password.withEncrypt("wooteco!"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("비밀번호는 대소문자를 포함해야 합니다.");
    }

    @DisplayName("비밀번호는 특수문자(!,@,?,-)를 포함해야 한다.")
    @Test
    void containsSpecialCharacters() {
        assertThatThrownBy(() -> Password.withEncrypt("Wooteco"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining("비밀번호는 특수문자(!,@,?,-)를 포함해야 합니다");
    }

    @DisplayName("객체 생성시 비밀번호를 암호화한다.")
    @Test
    void encryptPassword() {
        Password password = Password.withEncrypt("Wooteco123!");
        assertThat(password.getValue()).isNotEqualTo("Wooteco123");
    }

    @DisplayName("비밀번호가 일치한다면 true를 반환한다.")
    @Test
    void isSameAs(){
        Password password  = Password.withEncrypt("Wooteco123!");
        boolean result = password.isSameAs("Wooteco123!");
        assertThat(result).isTrue();
    }

    @DisplayName("비밀번호가 일치하지 않는다면 false를 반환한다.")
    @Test
    void isNotSameAs(){
        Password password  = Password.withEncrypt("Wooteco123!");
        String expected = PasswordEncoder.encrypt("Wooteco123?");
        boolean result = password.isSameAs(expected);
        assertThat(result).isFalse();
    }
}
