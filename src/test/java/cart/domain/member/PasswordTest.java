package cart.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("비밀번호에 빈 값이 들어오면 예외가 발생한다.")
    void create_blankPassword(String password) {
        // expect
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 빈 값이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"123:456", ":123456", "123456:"})
    @DisplayName("비밀번호에 :이 포함되면 예외가 발생해야 한다.")
    void create_containColonPassword(String password) {
        // expect
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호에 \":\"가 포함될 수 없습니다.");
    }

    @Test
    @DisplayName("이메일의 길이가 4자리 미만이면 예외가 발생해야 한다.")
    void create_overThan4Characters() {
        // expect
        assertThatThrownBy(() -> new Password("123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호의 길이는 4자리 이상이어야 합니다.");
    }

    @Test
    @DisplayName("이메일의 길이가 20자리를 초과하면 예외가 발생해야 한다.")
    void create_lessThan20Characters() {
        // expect
        assertThatThrownBy(() -> new Password("123456789012345678901")) // 21
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호의 길이는 20자리 이하여야 합니다.");
    }

    @Test
    @DisplayName("비밀번호가 정상적으로 생성되어야 한다.")
    void create_success() {
        // given
        Password password = new Password("123456");

        // expect
        assertThat(password.getPassword())
                .isEqualTo("123456");
    }
}
