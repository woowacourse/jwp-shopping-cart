package cart.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.user.UserFieldNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UserEmailTest {

    @Test
    @DisplayName("UserEmail이 생성된다.")
    void createUserEmailSuccess() {
        UserEmail userEmail = assertDoesNotThrow(() -> new UserEmail("a@a.com"));

        assertThat(userEmail).isExactlyInstanceOf(UserEmail.class);
    }

    @Test
    @DisplayName("email 형식이 아니면 예외가 발생한다.")
    void createUserEmailFailWithNotEmailFormat() {
        assertThatThrownBy(() -> new UserEmail("Not Email Format"))
                .isInstanceOf(UserFieldNotValidException.class)
                .hasMessage("이메일은 이메일 형식만을 입력할 있습니다.");
    }

    @ParameterizedTest(name = "email이 a@a.com일 때, email로 {0}을 전달하면 {1}을 반환한다.")
    @CsvSource(value = {"a@a.com:true", "b@b.com:false"}, delimiter = ':')
    void matches(String email, boolean expected) {
        UserEmail userEmail = new UserEmail("a@a.com");

        assertThat(userEmail.matches(email)).isEqualTo(expected);
    }
}
