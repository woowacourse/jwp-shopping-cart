package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.dataempty.CustomerDataEmptyException;
import woowacourse.shoppingcart.exception.dataformat.CustomerDataFormatException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Nickname 도메인 테스트")
class NicknameTest {

    @DisplayName("닉네임에 null 을 입력하면 예외가 발생한다.")
    @Test
    void nicknameNullException() {
        // when & then
        assertThatThrownBy(() -> new Nickname(null))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("닉네임에 빈값을 입력하면 예외가 발생한다.")
    void nicknameBlankException(String nickname) {
        // when & then
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "aaaaaaaaaaa", "!@#$"})
    @DisplayName("닉네임이 영문, 한글, 숫자를 조합하여 2 ~ 10 자가 아니면 예외가 발생한다.")
    void nicknameFormatException(String nickname) {
        // when & then
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("닉네임은 영문, 한글, 숫자를 조합하여 2 ~ 10 자를 입력해주세요.");
    }
}
