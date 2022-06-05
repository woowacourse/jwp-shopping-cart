package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.IllegalFormException;

class PlainPasswordTest {

    @DisplayName("유저의 비밀번호는 대문자, 소문자, 숫자, 특수문자가 1개씩 포함되며, 길이에 맞게 작성되면 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"Aa!45678", "Aa!4567_", "Aa!4567a"})
    void construct(String password) {
        assertDoesNotThrow(() -> new PlainPassword(password));
    }

    @DisplayName("비밀번호가 정해진 길이에 맞지 않는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"Aa!4567", "Aa!45678901234567"})
    void validatePasswordLength(String password) {
        assertThatThrownBy(() -> new PlainPassword(password))
                .isExactlyInstanceOf(IllegalFormException.class)
                .hasMessageContaining("비밀번호의 입력 형식에 맞지 않습니다.");
    }

    @DisplayName("유저의 비밀번호에 알맞지 않은 형식이 있는 경우 예외가 발생한다.")
    @Nested
    class PasswordPatternTest {

        @DisplayName("대문자 미포함")
        @Test
        void notContainUppercase() {
            assertThatThrownBy(() -> new PlainPassword("password123!"))
                    .isExactlyInstanceOf(IllegalFormException.class)
                    .hasMessageContaining("비밀번호의 입력 형식에 맞지 않습니다.");
        }

        @DisplayName("소문자 미포함")
        @Test
        void notContainLowercase() {
            assertThatThrownBy(() -> new PlainPassword("PASSWORD123!"))
                    .isExactlyInstanceOf(IllegalFormException.class)
                    .hasMessageContaining("비밀번호의 입력 형식에 맞지 않습니다.");
        }

        @DisplayName("특수문자 미포함")
        @Test
        void notContainSpecial() {
            assertThatThrownBy(() -> new PlainPassword("Password123"))
                    .isExactlyInstanceOf(IllegalFormException.class)
                    .hasMessageContaining("비밀번호의 입력 형식에 맞지 않습니다.");
        }

        @DisplayName("숫자 미포함")
        @Test
        void notContainNumber() {
            assertThatThrownBy(() -> new PlainPassword("Password!!!"))
                    .isExactlyInstanceOf(IllegalFormException.class)
                    .hasMessageContaining("비밀번호의 입력 형식에 맞지 않습니다.");
        }

        @DisplayName("한글 포함")
        @Test
        void containKorean() {
            assertThatThrownBy(() -> new PlainPassword("Password123!ㅁ"))
                    .isExactlyInstanceOf(IllegalFormException.class)
                    .hasMessageContaining("비밀번호의 입력 형식에 맞지 않습니다.");
        }

        @DisplayName("허용되지 않은 특수문자 포함")
        @Test
        void containNotAllowSpecial() {
            assertThatThrownBy(() -> new PlainPassword("Password123~"))
                    .isExactlyInstanceOf(IllegalFormException.class)
                    .hasMessageContaining("비밀번호의 입력 형식에 맞지 않습니다.");
        }

        @DisplayName("공백 포함")
        @Test
        void containSpace() {
            assertThatThrownBy(() -> new PlainPassword("Password123 "))
                    .isExactlyInstanceOf(IllegalFormException.class)
                    .hasMessageContaining("비밀번호의 입력 형식에 맞지 않습니다.");
        }
    }
}
