package cart.domain;

import cart.domain.product.ProductName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class ProductNameTest {

    @Test
    @DisplayName("이름이 null 일 경우 예외 발생")
    void nameNull() {
        assertThatThrownBy(() -> new ProductName(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이름의 길이가 0일 경우 예외 발생")
    void nameEmpty() {
        assertThatThrownBy(() -> new ProductName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ProductName.NAME_LENGTH_ERROR_MESSAGE);
    }

    @ParameterizedTest
    @DisplayName("이름의 길이가 0일 경우 예외 발생")
    @ValueSource(strings = {"", " "})
    void nameBlank(String name) {
        assertThatThrownBy(() -> new ProductName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ProductName.NAME_LENGTH_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("이름의 길이가 64 초과일 경우 예외 발생")
    void nameLengthMoreThanMax() {
        String name = "a".repeat(65);
        assertThatThrownBy(() -> new ProductName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ProductName.NAME_LENGTH_ERROR_MESSAGE);
    }

    @Test
    @DisplayName("이름의 길이가 정상일 경우")
    void nameSuccess() {
        assertAll(
                () -> assertDoesNotThrow(() -> new ProductName("이")),
                () -> assertDoesNotThrow(() -> new ProductName("a".repeat(64)))
        );
    }
}
