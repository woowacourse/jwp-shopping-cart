package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class UsernameTest {

    @Test
    void 아이디가_4글자_미만인_경우_예외발생() {
        assertThatThrownBy(() -> new Username("3글자"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 아이디가_20글자_초과인_경우_예외발생() {
        assertThatThrownBy(() -> new Username("123456789012345678901"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
