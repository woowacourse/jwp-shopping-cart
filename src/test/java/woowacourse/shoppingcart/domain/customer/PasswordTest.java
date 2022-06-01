package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class PasswordTest {

    @DisplayName("비밀번호를 생성한다")
    @Test
    void construct() {
        assertThatNoException().isThrownBy(() -> new Password("forky1234!"));
    }

    @DisplayName("비밀번호가 공백이면 예외가 발생한다")
    @Test
    void construct_blank() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Password(" "))
                .withMessageContaining("공백");
    }

    @DisplayName("비밀번호가 8글자 미만이거나 20글자 초과이면 예외가 발생한다")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"abcd2*g", "forkyforkyforky1234*!"})
    void construct_length(String password) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Password(password))
                .withMessageContaining("8자 이상 20자 이하");
    }

    @DisplayName("비밀번호에 한글이 포함되면 예외가 발생한다")
    @Test
    void construct_korean() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Password("복희12345~!"))
                .withMessageContaining("알파벳, 숫자, 특수문자");
    }

    @DisplayName("비밀번호에 !@#$%^* 이외의 특수문자가 포함되면 예외가 발생한다")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"forky&forky", "kth1234`", "kei+forky", "forky=genius"})
    void construct_character(String password) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Password(password))
                .withMessageContaining("알파벳, 숫자, 특수문자");
    }

    @DisplayName("비밀번호 일치 여부를 확인한다.")
    @ParameterizedTest(name = "{0}")
    @CsvSource({"kth@990303, true", "forky@123, false"})
    void has_same_password(String password, boolean expected) {
        Password given = new Password("kth@990303");
        assertThat(given.hasSamePassword(password)).isEqualTo(expected);
    }
}