package cart.entity;

import cart.exception.customexceptions.NotValidDataException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {

    @Test
    void 상품의_이름이_공백이면_예외를_던진다() {
        assertThatThrownBy(() -> new Product("  ", "fdsfdsf", 3000))
                .isInstanceOf(NotValidDataException.class);
    }

    @Test
    void 상품의_이미지경로가_공백이면_예외를_던진다() {
        assertThatThrownBy(() -> new Product("fdsfdsfds", "   ", 3000))
                .isInstanceOf(NotValidDataException.class);
    }

    @Test
    void 상품의_가격이_범위를_벗어나면_예외를_던진다() {
        assertThatThrownBy(() -> new Product("jljlkjl", "fdsfdsf", 2_000_000_000))
                .isInstanceOf(NotValidDataException.class);
        assertThatThrownBy(() -> new Product("jljlkjl", "fdsfdsf", -1))
                .isInstanceOf(NotValidDataException.class);
    }
}
