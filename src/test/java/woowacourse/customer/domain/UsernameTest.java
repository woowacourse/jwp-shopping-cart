package woowacourse.customer.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.customer.exception.InvalidUsernameException;

class UsernameTest {

    @DisplayName("username을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abc", "15lengthgoooood"})
    void createUsername(final String value) {
        assertDoesNotThrow(() -> new Username(value));
    }

    @DisplayName("길이가 맞지 않는 username을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"ab", "16length16length"})
    void createInvalidLengthUsername(final String value) {
        assertThatThrownBy(() -> new Username(value))
            .isInstanceOf(InvalidUsernameException.class)
            .hasMessage("사용자 이름의 길이는 3자 이상 15자 이하여야 합니다.");
    }

    @DisplayName("패턴이 맞지 않는 username을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"한글입니다", "@!&@#&!"})
    void createInvalidPatternUsername(final String value) {
        assertThatThrownBy(() -> new Username(value))
            .isInstanceOf(InvalidUsernameException.class)
            .hasMessage("사용자 이름은 영어와 숫자로 이루어져야 합니다.");
    }
}
