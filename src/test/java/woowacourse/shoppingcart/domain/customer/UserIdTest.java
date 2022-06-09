package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.dataempty.CustomerDataEmptyException;
import woowacourse.shoppingcart.exception.dataformat.CustomerDataFormatException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("UserId 도메인 테스트")
class UserIdTest {

    @DisplayName("아이디에 null 을 입력하면 예외가 발생한다.")
    @Test
    void userIdNullException() {
        // when & then
        assertThatThrownBy(() -> new UserId(null))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("아이디에 빈값을 입력하면 예외가 발생한다.")
    void userIdBlankException(String userId) {
        // when & then
        assertThatThrownBy(() -> new UserId(userId))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @DisplayName("아이디가 이메일 형식이 아니면 예외가 발생한다.")
    @Test
    void userIdFormatException() {
        // when & then
        assertThatThrownBy(() -> new UserId("userId"))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("아이디는 이메일 형식으로 입력해주세요.");
    }
}
