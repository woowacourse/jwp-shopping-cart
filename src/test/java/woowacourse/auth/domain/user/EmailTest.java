package woowacourse.auth.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.auth.exception.InvalidEmailFormatException;

class EmailTest {

    @DisplayName("이메일 문자열을 전달받아 생성된다.")
    @Test
    void constructor() {
        // given
        String email = "devhudi@gmail.com";

        // when
        Email actual = new Email(email);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("올바르지 않은 이메일 포맷을 전달하면 예외가 발생한다.")
    @ValueSource(strings = {"", "devhudi", "devhudi@gmail", "devhudi@.com", "@naver.com"})
    @ParameterizedTest
    void constructor_invalidFormat(String input) {
        // when & then
        assertThatThrownBy(() -> new Email(input))
                .isInstanceOf(InvalidEmailFormatException.class);
    }
}
