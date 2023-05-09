package cart.domain.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("이메일에 빈 값이 들어오면 예외가 발생한다.")
    void create_blankEmail(String email) {
        // expect
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일은 빈 값이 될 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {":aaa@naver.com", "aaa@na:ver.com", "aaa@naver.com:"})
    @DisplayName("이메일에 :이 포함되면 예외가 발생해야 한다.")
    void create_containColonEmail(String email) {
        // expect
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일에 \":\"가 포함될 수 없습니다.");
    }
    
    @Test
    @DisplayName("이메일의 길이가 30자리를 넘어가면 예외가 발생해야 한다.")
    void create_overThan20Characters() {
        // expect
        assertThatThrownBy(() -> new Email("1234567890123456789@123456789.123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일의 길이는 30자리 이하여야 합니다.");
    }

    @Test
    @DisplayName("이메일의 형식이 아니면 예외가 발생해야 한다.")
    void create_invalidEmail() {
        // expect
        assertThatThrownBy(() -> new Email("glen"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 이메일 형식이 아닙니다.");
    }

    @Test
    @DisplayName("이메일이 정상적으로 생성되어야 한다.")
    void create_success() {
        // given
        Email email = new Email("glen@naver.com");

        // expect
        assertThat(email.getEmail())
                .isEqualTo("glen@naver.com");
    }
}
