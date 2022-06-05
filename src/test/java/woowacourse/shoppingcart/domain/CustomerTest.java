package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CustomerTest {

    @DisplayName("전화번호 형식이 맞지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"011-1234-5667", "010-123-4567", "010-12345-6788", "010-1234-56789", "010-1234-567"})
    void validPhone(String phone) {
        assertThatThrownBy(() -> new Customer("email", "Abcd1234!!", "name", phone, "address"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("전화번호의 형식이 맞지 않습니다.");

    }

    @DisplayName("이름의 길이가 30자를 넘으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
    void validName(String name) {
        assertThatThrownBy(
                () -> new Customer("email", "Abcd1234!!", name, "010-1234-5678", "address"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이름은 1자 이상, 30자 이하여야 합니다.");
    }

    @DisplayName("비밀번호의 형식이 맞지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"aB1234!", "abc12345*", "ABC1235*", "Abc123456", "Abcdef!!!", "Abcdef12345678!@#"})
    void validPassword(String pw) {
        assertThatThrownBy(() -> new Customer("email", pw, "name", "010-1234-5678", "address"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호의 형식이 맞지 않습니다.");

    }
}
