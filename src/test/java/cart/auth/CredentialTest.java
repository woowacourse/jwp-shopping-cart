package cart.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CredentialTest {

    @DisplayName("전달값과 패스워드가 일치하지 않으면 true를 반환한다.")
    @Test
    void is_true_if_has_not_same_password() {
        //given
        String givenPassword = "password";
        Credential credential = new Credential(1L, "email@naver.com", givenPassword);
        //when
        boolean actual = credential.isWrongPassword(givenPassword + "any");
        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("전달값과 패스워드가 일치하면 false를 반환한다.")
    @Test
    void is_false_if_has_same_password() {
        //given
        String givenPassword = "password";
        Credential credential = new Credential(1L, "email@naver.com", givenPassword);
        //when
        boolean actual = credential.isWrongPassword(givenPassword);
        //then
        assertThat(actual).isFalse();
    }
}
