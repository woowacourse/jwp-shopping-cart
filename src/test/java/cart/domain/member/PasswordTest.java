package cart.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {"password", "password1234", "password1234!@#$"})
    @DisplayName("Password를 생성할 수 있다.")
    void create(String value) {
        final Password password = new Password(value);
        assertThat(password.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    "})
    @DisplayName("Password에 빈 값이 들어오면 예외가 발생한다.")
    void validateNull(String value) {
        assertThatThrownBy(() -> new Password(value)).isInstanceOf(IllegalArgumentException.class);
    }
}
