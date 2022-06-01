package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class NIcknameTest {

    @Test
    void 닉네임이_0글자인_경우_예외발생() {
        assertThatThrownBy(() -> new Nickname(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 닉네임이_10글자_초과인_경우_예외발생() {
        assertThatThrownBy(() -> new Nickname("12345678901"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
