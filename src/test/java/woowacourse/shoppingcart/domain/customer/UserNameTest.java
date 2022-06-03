package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.IllegalFormException;

class UserNameTest {

    @DisplayName("유저의 알맞은 소문자, 숫자, 언더바가 길이에 맞게 작성되면 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"ab_12", "abcde", "12345", "_____"})
    void construct(String userName) {
        assertDoesNotThrow(() -> new UserName(userName));
    }

    @DisplayName("유저의 이름이 정해진 길이에 맞지 않는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1234", "123456789012345678901"})
    void validateUserNameLength(String userName) {
        assertThatThrownBy(() -> new UserName(userName))
                .isExactlyInstanceOf(IllegalFormException.class)
                .hasMessageContaining("이름의 입력 형식에 맞지 않습니다.");
    }

    @DisplayName("유저의 이름에 알맞지 않은 형식이 있는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"12345A", "12345ㅁ", "12345!", "12345 ",})
    void validateUserNamePattern(String userName) {
        assertThatThrownBy(() -> new UserName(userName))
                .isExactlyInstanceOf(IllegalFormException.class)
                .hasMessageContaining("이름의 입력 형식에 맞지 않습니다.");
    }
}
