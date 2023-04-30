package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("Email 은(는)")
class EmailTest {

    @ParameterizedTest(name = "이메일 형식이 올바르지 않다면 오류이다. ex = [{0}]")
    @ValueSource(strings = {
            "mallang@woowa",
            "mallang!woowa.com"
    })
    void 이메일_형식이_올바르지_않는다면_오류(final String wrongEmail) {
        // when & then
        assertThatThrownBy(() ->
                Email.email(wrongEmail)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이메일_형식이_올바르면_생성된다() {
        // when & then
        assertDoesNotThrow(
                () -> Email.email("mallang@woowa.com")
        );
    }
}
