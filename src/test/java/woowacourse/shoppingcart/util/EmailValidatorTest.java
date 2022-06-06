package woowacourse.shoppingcart.util;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailValidatorTest {

    @DisplayName("이메일 형식이 유효한 경우를 성공")
    @ParameterizedTest
    @ValueSource(strings = {"abc@abc.com", "seungpapang@gmail.com", "angie@hanmail.net"})
    void validate(String email) {
        assertThatCode(() -> EmailValidator.validate(email))
                .doesNotThrowAnyException();
    }

    @DisplayName("이메일 형식이 유효하지 않을 경우 예외발생.")
    @ParameterizedTest
    @ValueSource(strings = {".@.", "!@sdf.com", "123123@sdfsdf"})
    void throwExceptionValidate(String email) {
        assertThatThrownBy(() -> EmailValidator.validate(email))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
