package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("validateIsBlank() : 이름을 공백으로 입력 시 IllegalArgumentException가 발생합니다.")
    void test_validateIsBlank_IllegalArgumentException(final String value) throws Exception {
        //when & then
        assertThatThrownBy(() -> new Name(value))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
