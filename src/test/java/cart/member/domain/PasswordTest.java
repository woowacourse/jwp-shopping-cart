package cart.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PasswordTest {

    @Test
    @DisplayName("비밀번호가 숫자와 알파벳으로만 이루어지고 길이가 8자 이상 40자 이하면 정상 생성됩니다")
    void acceptedPassword() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> new Password("a".repeat(8))),
                () -> assertThatNoException().isThrownBy(() -> new Password("1".repeat(40))),
                () -> assertThatNoException().isThrownBy(() -> new Password("1aA2".repeat(10)))
        );
    }

    @Test
    @DisplayName("비밀번호가 숫자와 알파벳으로만 이루어지지 않거나 길이가 8자 미만 40자 초과면 예외를 발생시킵니다")
    void unacceptablePassword() {
        assertAll(
                () -> assertThatThrownBy(() -> new Password("a1@b2c3asdf"))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("비밀 번호는 오직 숫자와 영어로 구성됩니다."),
                () -> assertThatThrownBy(() -> new Password("a".repeat(7)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("비밀 번호는 8 ~ 40자로 구성됩니다."),
                () -> assertThatThrownBy(() -> new Password("1".repeat(41)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("비밀 번호는 8 ~ 40자로 구성됩니다.")
        );
    }
}
