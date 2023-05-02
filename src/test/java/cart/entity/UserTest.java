package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UserTest {

    @DisplayName("이메일의 길이가 5 ~ 30 글자가 아니면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"abcd", "abcdefghijklmnopqrstuvwxyzABCDE"})
    void validateEmail(String email) {
        //then
        assertThatThrownBy(() -> new User(email, "password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일은 5 ~ 30 길이여야 합니다.");
    }

    @DisplayName("이메일은 @를 한 개 포함하지 않으면 예외가 발생한다")
    @Test
    void validateEmail2() {
        //then
        assertThatThrownBy(() -> new User("abcde", "password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일은 '@'를 한 개 포함해야 합니다.");
    }

    @DisplayName("비밀번호는 6 ~ 30 글자가 아니면 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"abcde", "abcdefghijklmnopqrstuvwxyzABCDE"})
    void validateValidate(String password) {
        //then
        assertThatThrownBy(() -> new User("email@naver.com", password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 6 ~ 30 길이여야 합니다.");
    }

    @DisplayName("유저를 생성할 수 있다.")
    @Test
    void createUser() {
        //then
        assertDoesNotThrow(() -> new User("email@naver.com", "password"));
    }
}
