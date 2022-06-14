package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlainPasswordTest {

    @DisplayName("비밀번호 길이가 8~20자 이고, 대소문자, 숫자, 특수문자가 1개 이상 포함되면 비밀번호가 생성된다.")
    @ParameterizedTest
    @CsvSource(value = {"Abcd123!", "longlongPassword123@"})
    void makePassword(String password) {
        assertThat(new PlainPassword(password)).isNotNull();
    }

    @DisplayName("비밀번호가 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwWhenPasswordNullOrEmpty(String password) {
        assertThatThrownBy(() ->
                new PlainPassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 비어있을 수 없습니다.");
    }

    @DisplayName("비밀번호 길이가 8~20자를 벗어나면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"Abc123!", "Abc123abc123abc123abc123!!"})
    void throwWhenPasswordInvalidLength(String password) {
        assertThatThrownBy(() ->
                new PlainPassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 길이는 8~20자를 만족해야 합니다.");
    }

    @DisplayName("비밀번호에 대소문자, 숫자, 특수문자가 1개 이상 포함되지 않으면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"12345678aa", "aA!!!!Aa", "korinnee123", "qwe123!!!", "tjdtksdlWkd"})
    void throwWhenPasswordInvalidPattern(String password) {
        assertThatThrownBy(() ->
                new PlainPassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 대소문자, 숫자, 특수문자가 반드시 1개 이상 포함되어야 합니다.");
    }
}
