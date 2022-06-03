package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidUsernameException;

public class UsernameTest {

    @Test
    @DisplayName("회원 이름에는 공백이 들어갈 수 없다.")
    void validateSpace() {
        assertThatThrownBy(() -> new Username("re nnon"))
                .isInstanceOf(InvalidUsernameException.class)
                .hasMessage("회원 이름에는 공백이 들어갈 수 없습니다.");
    }

    @Test
    @DisplayName("회원 이름은 32자를 초과할 수 없다.")
    void validateLength() {
        assertThatThrownBy(() -> new Username("rennonrennonrennonrennonrennonren"))
                .isInstanceOf(InvalidUsernameException.class)
                .hasMessage("회원 이름은 32자 이하입니다.");
    }
}
