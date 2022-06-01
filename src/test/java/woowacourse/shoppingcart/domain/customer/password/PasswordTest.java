package woowacourse.shoppingcart.domain.customer.password;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {
    @DisplayName("고객이 입력한 password가 null이면 예외를 던진다.")
    @Test
    void create_error_null() {
        assertThatThrownBy(() -> Password.createRaw(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("비밀번호는 필수 입력 사항입니다.");
    }

    @DisplayName("고객이 입력한 password 길이가 맞지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"test", "testtesttesttesttesttest"})
    void create_error_passwordLength(String password) {
        assertThatThrownBy(() -> Password.createRaw(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 형식이 올바르지 않습니다. (영문자, 숫자, 특수문자!, @, #, $, %, ^, &, *, (, )를 모두 사용, 8자 이상 16자 이내)");
    }

    @DisplayName("고객이 입력한 password 형식이 맞지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"testtest", "12345678", "test1234", "test123?", "!!!!!!!!", "test!!!!", "1234567!"})
    void create_error_passwordFormat(String password) {
        assertThatThrownBy(() -> Password.createRaw(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 형식이 올바르지 않습니다. (영문자, 숫자, 특수문자!, @, #, $, %, ^, &, *, (, )를 모두 사용, 8자 이상 16자 이내)");
    }

    @DisplayName("고객이 입력한 password 형식에 맞으면 password이 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"test123!", "q1w2e3r4!"})
    void create(String password) {
        assertDoesNotThrow(() -> Password.createRaw(password));
    }
}
