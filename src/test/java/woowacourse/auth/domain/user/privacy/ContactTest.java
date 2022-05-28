package woowacourse.auth.domain.user.privacy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.auth.exception.format.InvalidContactFormatException;

class ContactTest {
    @DisplayName("전화번호 문자열을 전달받아 생성된다.")
    @Test
    void constructor() {
        // given
        String contact = "01011111111";

        // when
        Contact actual = new Contact(contact);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("올바르지 않은 전화번호 포맷을 전달하면 예외가 발생한다.")
    @ValueSource(strings = {"", "0101111", "010111111112", "010-1111-1111", "010 1111 1111", "0101111zzzz"})
    @ParameterizedTest
    void constructor_invalidFormat(String input) {
        // when & then
        assertThatThrownBy(() -> new Contact(input))
                .isInstanceOf(InvalidContactFormatException.class);
    }
}
