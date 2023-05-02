package cart.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("")
    void equals() {
        //given
        User user = new User("rosie@wooteco.com", "1234");
        //when
        User otherUser = new User("rosie@wooteco.com", "4321");
        //then
        Assertions.assertThat(otherUser).isEqualTo(user);
    }

}