package woowacourse.shoppingcart.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    @DisplayName("Password 규칙에 맞는 문자열로 Password를 생성한다.")
    @Test
    void create() {
        String value = "abcde12!";

        Password password = new Password(value);

        assertThat(password.getValue()).isEqualTo("abcde12!");
    }

    @DisplayName("Password의 길이가 8자 이상, 20자 이하가 아닌 경우 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcd12!", "abcde12345abcde12345!"})
    void create_InvalidLength(String value) {
        assertThatThrownBy(() -> new Password(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("영문자, 숫자, 특수문자가 모두 포함되지 않을 경우 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1234567!", "abcdefg!", "a1234567"})
    void create_WithoutNecessaryChar(String value) {
        assertThatThrownBy(() -> new Password(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("영문자, 숫자, 특수문자 이외의 문자가 포함되었을 경우 예외를 반환한다.")
    @Test
    void create_InvalidChar() {
        String value = "a비밀번호486!";

        assertThatThrownBy(() -> new Password(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 형식이 올바르지 않습니다.");
    }

    @DisplayName("문자열이 비밀번호와 일치하는지 여부를 반환한다.")
    @ParameterizedTest
    @CsvSource({"1q2w3e4r!,true", "1q2w3e4r@,false"})
    void isSameValue(String value, boolean expected) {
        Password password = new Password("1q2w3e4r!");

        boolean actual = password.matches(value);

        assertThat(actual).isEqualTo(expected);
    }
}
