package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.exception.InvalidEmailFormatException;

class EmailTest {

    @ParameterizedTest
    @DisplayName("이메일 형식에 맞지 않을 경우 예외가 발생한다.")
    @EmptySource
    @ValueSource(strings = {"asdasd", "asd@asd", "abc.com"})
    void new_invalidEmailFormat_throwsException(final String invalidEmailValue) {
        // given, when, then
        assertThatThrownBy(() -> new Email(invalidEmailValue))
                .isInstanceOf(InvalidEmailFormatException.class);
    }

    @ParameterizedTest
    @DisplayName("올바른 이메일 형식일 경우 정상적으로 이메일을 생성한다.")
    @ValueSource(strings = {"asdasd@gmail.com", "ae@be.de", "123klay@naver.com"})
    void newEmail(final String value) {
        // given, when, then
        assertThatCode(() -> new Email(value))
                .doesNotThrowAnyException();
    }
}
