package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.badrequest.InvalidPasswordException;

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
}