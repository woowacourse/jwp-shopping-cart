package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {

    @DisplayName("Email과 Password를 입력받아 생성한다")
    @Test
    void create() {
        //given
        Email email = new Email("email.email.com");
        Password password = new Password("12345678");
        //when
        User user = new User(email, password);
        //then
        assertThat(user).isNotNull();
    }

    @DisplayName("Email 이나 Password가 없으면 예외를 반환한다")
    @Test
    void createExceptionWithPasswordNull() {
        //given
        Password password = new Password("12345678");
        //then
        assertThatThrownBy(() -> new User(null, password)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Email 이나 Password가 없으면 예외를 반환한다")
    @Test
    void createExceptionWithEmailNull() {
        //given
        Email email = new Email("email.email.com");
        //then
        assertThatThrownBy(() -> new User(email, null)).isInstanceOf(IllegalArgumentException.class);
    }
}
