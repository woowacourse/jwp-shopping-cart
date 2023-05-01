package cart.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MemberTest {
    @DisplayName("회원을 생성할 때")
    @Nested
    class Validate {

        @DisplayName("회원 이름은 null이거나 공백이")
        @Nested
        class Name {
            @ValueSource(strings = {"", " "})
            @ParameterizedTest(name = "맞을 경우 예외가 발생한다. [이름 : {0}]")
            void isBlank(final String name) {
                assertThatThrownBy(() -> new Member(name, "test@test.com", "test"))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @ValueSource(strings = {"hyena", "tony"})
            @ParameterizedTest(name = "아닐 경우 생성 성공한다. [이름 : {0}]")
            void isOk(final String name) {
                assertDoesNotThrow(() -> new Member(name, "test@test.com", "test"));
            }
        }

        @DisplayName("회원 이메일은 null이거나 공백이")
        @Nested
        class Email {
            @ValueSource(strings = {"", " "})
            @ParameterizedTest(name = "맞을 경우 예외가 발생한다. [이메일 : {0}]")
            void isBlank(final String email) {
                assertThatThrownBy(() -> new Member("hyena", email, "test"))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @ValueSource(strings = {"test@test.com", "tteesstt@tteesstt.ccoomm"})
            @ParameterizedTest(name = "아닐 경우 생성 성공한다. [이메일 : {0}]")
            void isOk(final String email) {
                assertDoesNotThrow(() -> new Member("hyena", email, "test"));
            }
        }

        @DisplayName("회원 비밀번호는 null이거나 공백이")
        @Nested
        class Password {
            @ValueSource(strings = {"", " "})
            @ParameterizedTest(name = "맞을 경우 예외가 발생한다. [비밀번호 : {0}]")
            void isBlank(final String password) {
                assertThatThrownBy(() -> new Member("hyena", "test@test.com", password))
                        .isInstanceOf(IllegalArgumentException.class);
            }

            @ValueSource(strings = {"!Jifji293", "AJSD(!JFK#N!KFN3"})
            @ParameterizedTest(name = "아닐 경우 생성 성공한다. [비밀번호 : {0}]")
            void isOk(final String password) {
                assertDoesNotThrow(() -> new Member("hyena", "test@test.com", password));
            }
        }
    }
}
