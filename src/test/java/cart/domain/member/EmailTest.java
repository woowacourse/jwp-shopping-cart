package cart.domain.member;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class EmailTest {

    @Test
    void 이메일을_정상적으로_등록할_수_있다() {
        final String input = "test123@test.com";

        assertDoesNotThrow(() -> new Email(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"@test.com", "test@test", "test.com"})
    void 유효하지_않은_이메일_형식일_경우_예외가_발생한다(final String input) {
        assertThatThrownBy(() -> new Email(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 이메일 형식입니다");
    }
}
