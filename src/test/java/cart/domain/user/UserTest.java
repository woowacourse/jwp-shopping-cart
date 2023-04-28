package cart.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
