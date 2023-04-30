package cart.domain.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NicknameTest {

    @ParameterizedTest
    @ValueSource(strings = {"!12", "!#@", "132$"})
    @DisplayName("닉네임이 한글, 영어, 숫자가 아니면 예외가 발생한다.")
    void wrongNickname_not_match_regex(String nickname) {
        // when, then
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임은 한글, 영어, 숫자로 된 2자 이상 8자 이하여야합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"가", "123456789"})
    @DisplayName("닉네임이 2자 이상 8자 이하가 아니면 예외가 발생한다.")
    void wrongLengthNickname(String nickname) {
        // when, then
        assertThatThrownBy(() -> new Nickname(nickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임은 한글, 영어, 숫자로 된 2자 이상 8자 이하여야합니다.");
    }
}
