package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("Password 은(는)")
class PasswordTest {

    @Test
    void 일치_여부를_확인한다() {
        // given
        final Password password1 = Password.from("1234");
        final Password password2 = Password.from("1234");
        final Password password3 = Password.from("12345");

        // when & then
        assertThat(password1.match(password2)).isTrue();
        assertThat(password3.match(password2)).isFalse();
    }
}
