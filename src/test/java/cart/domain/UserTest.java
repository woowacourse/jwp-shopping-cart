package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("이메일이 같으면 같은 유저이다")
    @Test
    void identifyUserByEmail() {
        User user = new User(1, "0@chll.it", "verysecurepassword");
        User otherUser = new User(2, "0@chll.it", "weakpassword");

        assertThat(user).isEqualTo(otherUser);
    }
}