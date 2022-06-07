package woowacourse.shoppingcart.domain.value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.value.Nickname;

class NicknameTest {

    @DisplayName("5자 이하의 한글로 닉네임을 생성할 수 있다.")
    @Test
    void create() {
        String value = "닉네임";

        Nickname nickname = new Nickname(value);

        assertThat(nickname.getValue()).isEqualTo("닉네임");
    }

    @DisplayName("닉네임이 1~5자가 아닌 경우 예외를 반환한다.")
    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = "가나다라마바")
    void create_InvalidLength(String value) {
        assertThatThrownBy(() -> new Nickname(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 형식이 올바르지 않습니다.");
    }

    @DisplayName("닉네임에 한글 이외의 문자가 들어가면 예외를 반환한다.")
    @Test
    void create_InvalidChar() {
        String value = "abcde";

        assertThatThrownBy(() -> new Nickname(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임 형식이 올바르지 않습니다.");
    }
}
