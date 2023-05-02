package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductEntityTest {

    @Test
    @DisplayName("Id 를 제외한 필드에 null 이 들어오면 예외가 발생한다.")
    void invalid_constructor() {
        //expect
        assertAll(
                () -> assertThatThrownBy(() -> new ProductEntity(null, "image.jpg", 100))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new ProductEntity("name", null, 100))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new ProductEntity("name", "image.jpg", null))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("entity 생성 성공 케이스")
    void valid_create() {
        //expect
        assertThatCode(() -> new ProductEntity("name", "image", 100))
                .doesNotThrowAnyException();
    }

}
