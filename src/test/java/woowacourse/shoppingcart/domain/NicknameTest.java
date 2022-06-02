package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.badrequest.InvalidNicknameException;

class NicknameTest {

    @ParameterizedTest
    @DisplayName("닉네임이 유효하지 않으면 예외를 던진다.")
    @ValueSource(strings = {"", "  ", "  a  ", "^^", "1 2 3 4 5 6 7 8"})
    void newNickname_invalidValue_exceptionThrown(final String value) {
        // when, then
        assertThatThrownBy(() -> new Nickname(value))
                .isInstanceOf(InvalidNicknameException.class);
    }
}