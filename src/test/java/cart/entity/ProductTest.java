package cart.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;

class ProductTest {

    @DisplayName("이름이 공백이면 예외가 발생한다")
    @EmptySource
    @ParameterizedTest
    void emptyNameTest(String name) {
        assertThatThrownBy(() -> new Product(name, "http://boxster.com", 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 20자 이하로 입력해주세요");
    }

    @DisplayName("이름이 20자 이상이면 예외가 발생한다")
    @Test
    void maxNameLengthTest() {
        assertThatThrownBy(() -> new Product(".".repeat(21), "http://boxster.com", 1000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품명은 20자 이하로 입력해주세요");
    }
}
