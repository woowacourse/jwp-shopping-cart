package woowacourse.shoppingcart.domain.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.badrequest.InvalidNicknameException;

public class NicknameTest {

    @DisplayName("닉네임은 2자 이상 8자 이하가 아니면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "a", "123456789"})
    void invalidFormNicknameException(String nickname) {
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(InvalidNicknameException.class);
    }
}
