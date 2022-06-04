package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class RawPasswordTest {

    @DisplayName("비밀번호를 생성한다")
    @Test
    void construct() {
        assertThatNoException().isThrownBy(() -> new RawPassword("forky1234!"));
    }

    @DisplayName("비밀번호가 공백이면 예외가 발생한다")
    @Test
    void construct_blank() {
        assertThatExceptionOfType(InvalidArgumentRequestException.class)
                .isThrownBy(() -> new RawPassword(" "))
                .withMessageContaining("8자 이상 20자 이하");
    }

    @DisplayName("비밀번호가 8글자 미만이거나 20글자 초과이면 예외가 발생한다")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"abcd2*g", "forkyforkyforky1234*!"})
    void construct_length(String password) {
        assertThatExceptionOfType(InvalidArgumentRequestException.class)
                .isThrownBy(() -> new RawPassword(password))
                .withMessageContaining("8자 이상 20자 이하");
    }

    @DisplayName("비밀번호에 한글이 포함되면 예외가 발생한다")
    @Test
    void construct_korean() {
        assertThatExceptionOfType(InvalidArgumentRequestException.class)
                .isThrownBy(() -> new RawPassword("복희12345~!"))
                .withMessageContaining("알파벳, 숫자, 특수문자");
    }

    @DisplayName("비밀번호에 !@#$%^* 이외의 특수문자가 포함되면 예외가 발생한다")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"forky&forky", "kth1234`", "kei+forky", "forky=genius"})
    void construct_character(String password) {
        assertThatExceptionOfType(InvalidArgumentRequestException.class)
                .isThrownBy(() -> new RawPassword(password))
                .withMessageContaining("알파벳, 숫자, 특수문자");
    }
}