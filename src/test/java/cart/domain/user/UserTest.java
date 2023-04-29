package cart.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UserTest {

    @Test
    @DisplayName("email, password를 전달하면 User가 생성된다.")
    void createUserSuccessWithEmailAndPassword() {
        User user = assertDoesNotThrow(() -> new User("a@a.com", "password"));

        assertThat(user).isExactlyInstanceOf(User.class);
    }

    @Test
    @DisplayName("id, email, password를 전달하면 User가 생성된다.")
    void createUserSuccessWithIdAndEmailAndPassword() {
        User user = assertDoesNotThrow(() -> new User(1L, "a@a.com", "password"));

        assertThat(user).isExactlyInstanceOf(User.class);
    }

    @ParameterizedTest(name = "email이 a@a.com, password가 password일 때 {0}, {1}을 전달하면 {2}를 반환한다")
    @CsvSource(value = {"a@a.com:password:true","b@b.com:password:false","a@a.com:not-password:false"}, delimiter = ':')
    void matches(String email, String password, boolean expected) {
        User user = new User("a@a.com", "password");

        assertThat(user.matches(email, password)).isEqualTo(expected);
    }
}
