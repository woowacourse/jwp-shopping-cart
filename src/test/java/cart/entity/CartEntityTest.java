package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CartEntityTest {

    @Test
    @DisplayName("entity 생성 성공 케이스")
    void create_success() {
        assertThatCode(() -> new CartEntity(1, 1))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Id 를 제외한 필드에 null 이 들어오면 예외가 발생한다.")
    void create_fail() {
        assertAll(
                () -> assertThatThrownBy(() -> new CartEntity(null, 1))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> new CartEntity(1, null))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
}