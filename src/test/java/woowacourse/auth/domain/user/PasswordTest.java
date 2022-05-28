package woowacourse.auth.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.auth.exception.format.InvalidPasswordFormatException;

class PasswordTest {

    @DisplayName("패스워드 문자열을 전달받아 생성한다.")
    @Test
    void constructor() {
        //given
        String password = "a1@12345";

        //when
        Password actual = new Password(password);

        //then
        assertThat(actual).isNotNull();
    }

    @DisplayName("올바르지 않은 패스워드 포맷을 전달하면 예외가 발생한다.")
    @ValueSource(strings = {"", "12345678", "a@qwerty", "@1ertyu", "asdqwdqew"})
    @ParameterizedTest
    void constructor_invalidFormat(String input) {
        // when & then
        assertThatThrownBy(() -> new Password(input))
                .isInstanceOf(InvalidPasswordFormatException.class);
    }
}
