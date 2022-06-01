package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {
    @DisplayName("password이 null이면 예외를 던진다.")
    @Test
    void create_error_null() {
        assertThatThrownBy(() -> new Password(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("비밀번호는 필수 입력 사항입니다.");
    }

    @DisplayName("password 길이가 맞지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"test", "testtesttesttesttesttest"})
    void create_error_passwordLength(String password) {
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 길이가 올바르지 않습니다. (길이: 8이상 16이하)");
    }

    @DisplayName("password 형식이 맞지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"testtest", "12345678", "test1234", "test123?", "!!!!!!!!", "test!!!!", "1234567!"})
    void create_error_passwordFormat(String password) {
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 형식이 올바르지 않습니다. (영문자, 숫자, 특수문자!, @, #, $, %, ^, &, *, (, )를 모두 사용)");
    }

    @DisplayName("password 형식에 맞으면 password이 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"test123!", "q1w2e3r4!"})
    void create(String password) {
        assertDoesNotThrow(() -> new Password(password));
    }
}
