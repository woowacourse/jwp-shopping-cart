package cart.domain.member;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PasswordTest {

    @Test
    void 비밀번호를_정상적으로_등록할_수_있다() {
        final String input = "test";

        assertDoesNotThrow(() -> new Password(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 비밀번호가_존재하지_않으면_예외가_발생한다(final String input) {
        assertThatThrownBy(() -> new Password(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호가 존재하지 않습니다");
    }
}
