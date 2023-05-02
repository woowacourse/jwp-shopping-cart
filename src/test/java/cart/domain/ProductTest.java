package cart.domain;

import cart.exception.InvalidProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @DisplayName("유효하지 않은 name이 들어올 시 InvalidProductException을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "10자가넘어가면안됩니다."})
    void validateName(String name) {
        // when, then
        assertThrows(
                InvalidProductException.class,
                () -> new Product(1L, name, "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 1000)
        );
    }

    @DisplayName("유효하지 않은 imageUrl이 들어올 시 InvalidProductException을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "imageUrl"})
    void validateImageUrl(String imageUrl) {
        // when, then
        assertThrows(
                InvalidProductException.class,
                () -> new Product(1L, "치킨", imageUrl, 1000)
        );
    }

    @DisplayName("유효하지 않은 price가 들어올 시 InvalidProductException을 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 100_000_001})
    void validatePrice(Integer price) {
        // when, then
        assertThrows(
                InvalidProductException.class,
                () -> new Product(1L, "치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", price)
        );
    }
}