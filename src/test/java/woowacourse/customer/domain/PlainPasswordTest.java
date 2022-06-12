package woowacourse.customer.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.customer.exception.InvalidPasswordException;

class PlainPasswordTest {

    @DisplayName("password를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcd1234", "abcdabcd123412341234"})
    void createPassword(final String value) {
        assertDoesNotThrow(() -> new PlainPassword(value));
    }

    @DisplayName("길이가 맞지 않는 password를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"7length", "20lengthover20lengthover"})
    void createInvalidLengthUsername(final String value) {
        assertThatThrownBy(() -> new PlainPassword(value))
            .isInstanceOf(InvalidPasswordException.class)
            .hasMessage("비밀번호의 길이는 8자 이상 20자 이하여야 합니다.");
    }

    @DisplayName("패턴이 맞지 않는 password을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"한글비밀번호에요", "@!&@#&!@"})
    void createInvalidPatternUsername(final String value) {
        assertThatThrownBy(() -> new PlainPassword(value))
            .isInstanceOf(InvalidPasswordException.class)
            .hasMessage("비밀번호는 영어와 숫자로 이루어져야 합니다.");
    }
}
