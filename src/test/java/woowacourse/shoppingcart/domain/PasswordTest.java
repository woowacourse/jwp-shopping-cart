package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PasswordTest {

    @DisplayName("입력한 비밀번호 값이 일치하는지 반환")
    @Test
    void equalsPassword() {
        Password password1 = new Password("12345678");
        Password password2 = new Password("12345678");

        assertThat(password1.equals(password2)).isTrue();
    }
}
