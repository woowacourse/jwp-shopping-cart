package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    @DisplayName("password를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcd1234", "abcdabcd123412341234"})
    void createPassword(final String value) {
        final Password password = new Password(value);

        assertThat(password.getValue()).isEqualTo(value);
    }

    @DisplayName("길이가 맞지 않는 password를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcd123", "abcdefghijklmnop12345"})
    void createInvalidLengthUsername(final String value) {
        assertThatThrownBy(() -> new Password(value))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("패턴이 맞지 않는 password을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"한글비밀번호에요", "@!&@#&!@"})
    void createInvalidPatternUsername(final String value) {
        assertThatThrownBy(() -> new Password(value))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
