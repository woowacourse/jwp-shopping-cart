package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.IllegalFormException;

class PlainPasswordTest {

    @DisplayName("유저의 알맞은 대문자, 소문자, 숫자, 특수문자가 1개씩 포함되며, 길이에 맞게 작성되면 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"Aa!45678", "Aa!4567_", "Aa!4567a"})
    void construct(String password) {
        assertDoesNotThrow(() -> new PlainPassword(password));
    }

    @DisplayName("비밀번호가 정해진 길이에 맞지 않는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"Aa!4567", "Aa!45678901234567"})
    void validatePasswordLength(String password) {
        assertThatThrownBy(() -> new PlainPassword(password))
                .isExactlyInstanceOf(IllegalFormException.class)
                .hasMessageContaining("비밀번호의 입력 형식에 맞지 않습니다.");
    }

    @DisplayName("유저의 이름에 알맞지 않은 형식이 있는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"Aa!4567ㅁ", "Aa!4567 ", "Aa!4567~", "Aa!4567?"})
    void validatePasswordPattern(String password) {
        assertThatThrownBy(() -> new PlainPassword(password))
                .isExactlyInstanceOf(IllegalFormException.class)
                .hasMessageContaining("비밀번호의 입력 형식에 맞지 않습니다.");
    }
}
