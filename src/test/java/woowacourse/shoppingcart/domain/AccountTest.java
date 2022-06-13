package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    @DisplayName("아이디 길이가 4~15자이고 소문자 혹은 숫자의 조합으로 이루어지면 아이디를 생성한다.")
    @ParameterizedTest
    @CsvSource(value = {"ham2", "hamcheeseburger"})
    void makeAccount(String value) {
        assertThat(new Account(value)).isNotNull();
    }

    @DisplayName("아이디가 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwWhenAccountNullOrEmpty(String value) {
        assertThatThrownBy(() -> new Account(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디는 비어있을 수 없습니다.");
    }

    @DisplayName("아이디 길이가 4~15자를 만족하지 않으면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"aaa", "hamCheeseBaconBurger"})
    void throwWhenInvalidLength(String value) {
        assertThatThrownBy(() -> new Account(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 길이는 4~15자를 만족해야 합니다.");
    }

    @DisplayName("아이디는 소문자 혹은 숫자의 조합으로 이루어져야 한다.")
    @ParameterizedTest
    @CsvSource(value = {"LEMON12", "abCD13!", "abcd!", "코린아이디1"})
    void throwWhenInvalidPattern(String value) {
        assertThatThrownBy(() -> new Account(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디는 영어 혹은 숫자의 조합으로 이루어져야 합니다.");
    }
}
