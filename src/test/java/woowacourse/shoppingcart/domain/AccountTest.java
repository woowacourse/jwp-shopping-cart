package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountTest {

    @DisplayName("아이디가 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void validateNullAndEmpty(String value) {
        assertThatThrownBy(() -> new Account(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디는 비어있을 수 없습니다.");
    }

    @DisplayName("아이디 길이가 4~15자를 만족하지 않으면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"aaa", "hamCheeseBaconBurger"})
    void validateLength(String value) {
        assertThatThrownBy(() -> new Account(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디 길이는 4~15자를 만족해야 합니다.");
    }

    @DisplayName("아이디가 대문자면 소문자로 변경하고 영어와 숫자가 아닌 다른 문자열은 제거한다")
    @ParameterizedTest
    @CsvSource(value = {"LEMON,lemon", "abCD13!,abcd13"})
    void convertAccount(String originValue, String targetValue) {
        final Account account = new Account(originValue);
        assertThat(account.getValue()).isEqualTo(targetValue);
    }
}
