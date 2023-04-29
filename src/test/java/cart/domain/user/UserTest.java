package cart.domain.user;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class UserTest {

    @Test
    void 정상적으로_생성된다() {
        // given
        final String email = "huchu@woowahan.com";
        final String password = "12345abc!!";

        // expect
        assertThatNoException().isThrownBy(() -> new User(email, password));
    }
}
