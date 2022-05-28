package woowacourse.auth.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.auth.exception.format.InvalidNameFormatException;

class NameTest {

    @DisplayName("이름 문자열을 전달받아 생성된다.")
    @Test
    void constructor() {
        // given
        String name = "배카라";

        // when
        Name actual = new Name(name);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("올바르지 않은 이름 포맷을 전달하면 예외가 발생한다.")
    @ValueSource(strings = {"", "hudi", "후디1", "후", "후디배카라짱", "후 디"})
    @ParameterizedTest
    void constructor_invalidFormat(String input) {
        // when & then
        assertThatThrownBy(() -> new Name(input))
                .isInstanceOf(InvalidNameFormatException.class);
    }
}
