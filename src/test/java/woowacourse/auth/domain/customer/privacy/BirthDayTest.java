package woowacourse.auth.domain.customer.privacy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BirthDayTest {

    @DisplayName("생년월일 문자열을 전달받아 생성된다.")
    @Test
    void constructor() {
        // given
        String birthDay = "1998-05-13";

        // when
        BirthDay actual = BirthDay.from(birthDay);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("올바르지 않은 생년월일 포맷을 전달하면 예외가 발생한다.")
    @ValueSource(strings = {"198-05-13", "1998-5-13", "1998-05-1", "1998--13", "1998-05-", "--"})
    @ParameterizedTest
    void constructor_invalidFormat(String input) {
        // when & then
        assertThatThrownBy(() -> BirthDay.from(input))
                .isInstanceOf(DateTimeParseException.class);
    }
}
