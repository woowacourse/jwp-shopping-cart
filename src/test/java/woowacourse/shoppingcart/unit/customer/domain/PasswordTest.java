package woowacourse.shoppingcart.unit.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.customer.domain.Password;
import woowacourse.shoppingcart.customer.exception.badrequest.InvalidPasswordException;

class PasswordTest {

    @ParameterizedTest
    @DisplayName("암호화 되지 않은 비밀번호가 유효하지 않으면 예외를 던진다.")
    @ValueSource(strings = {"", "  ", "  a  ", "123456789", "qwertqwert", "^1q2w3e^", "1q2w3e4",
            "0123456789qwertqwert0123456789qwertqwert9"})
    void fromPlain_invalidValue_exceptionThrown(final String value) {
        // when, then
        assertThatThrownBy(() -> Password.fromPlain(value))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @ParameterizedTest
    @DisplayName("암호화된 비밀번호가 유효하지 않으면 예외를 던진다.")
    @ValueSource(strings = {"", "  ", "  a  ", "hash-password",
            "$2b$10$Mvk2lhYGaXksfHsQalVbSuU0T3BRZAvNCruOKBmfMLlpKToff86ui",
            "$2a$11$Mvk2lhYGaXksfHsQalVbSuU0T3BRZAvNCruOKBmfMLlpKToff86ui",
            "$2a$10$Mvk2lhYGaXksfHsQalVbSuU0T3BRZAvNCruOKBmfMLlpKToff86uia",
            "$2a$10$Mvk2lhYGaXksfHsQalVbSuU0T3BRZAvNCruOKBmfMLlpKToff86u"})
    void fromHash_invalidValue_exceptionThrown(final String value) {
        // when, then
        assertThatThrownBy(() -> Password.fromHash(value))
                .isInstanceOf(InvalidPasswordException.class);
    }

    @ParameterizedTest
    @DisplayName("비밀번호 일치 여부를 판단한다.")
    @CsvSource(value = {"1q2w3e4r:true", "1q2w3e5t:false"}, delimiter = ':')
    void isSame_plainValue_booleanReturned(final String plainValue, final boolean expected) {
        // given
        final Password password = Password.fromPlain("1q2w3e4r");

        // when
        final boolean actual = password.isSame(plainValue);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}