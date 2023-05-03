package cart.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @ParameterizedTest
    @ValueSource(strings = {"mango@wooteco.com", "dd@wooteco.com"})
    @DisplayName("Email을 생성할 수 있다.")
    void create(String value) {
        final Email email = new Email(value);
        assertThat(email.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    "})
    @DisplayName("Email에 빈 값이 들어오면 예외가 발생한다.")
    void validateNull(String value) {
        assertThatThrownBy(() -> new Email(value)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"mango", "mango@" , "mango@wooteco"})
    @DisplayName("Email 형식을 만족하지 않으면 예외가 발생한다.")
    void validateFormat() {
        assertThatThrownBy(() -> new Email("mango")).isInstanceOf(IllegalArgumentException.class);
    }
}
