package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidEmailException;

class EmailTest {

    @Test
    @DisplayName("이메일은 이메일 형식이어야 한다.")
    void validateForm() {
        assertThatThrownBy(() -> new Email("rennonwoowacom"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }

    @Test
    @DisplayName("이메일은 64자를 초과할 수 없다.")
    void validateLength() {
        assertThatThrownBy(() -> new Email("rennonrennonrennonrennonrennonrennonrennonrennonrennon1@woowa.com"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("이메일은 64자 이하입니다.");
    }
}
