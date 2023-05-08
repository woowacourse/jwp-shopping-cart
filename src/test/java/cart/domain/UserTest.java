package cart.domain;

import static cart.TestFixture.EMAIL_0CHIL;
import static cart.TestFixture.PASSWORD_0CHIL;
import static cart.TestFixture.PASSWORD_BEAVER;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @DisplayName("이메일이 같으면 같은 유저이다")
    @Test
    void identifyUserByEmail() {
        User user = new User(1, EMAIL_0CHIL, PASSWORD_0CHIL);
        User otherUser = new User(2, EMAIL_0CHIL, PASSWORD_BEAVER);

        assertThat(user).isEqualTo(otherUser);
    }
}
