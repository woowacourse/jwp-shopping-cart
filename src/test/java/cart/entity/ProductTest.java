package cart.entity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.product.NullOrBlankException;
import cart.exception.product.PriceNotUnderZeroException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductTest {

    @ParameterizedTest
    @DisplayName("name과 image_url이 빈 값이나 null이면 에러를 발생한다.")
    @CsvSource(value = {" , ,1000", "연필, ,1000", " ,이미지,1000"})
    void validate_null_or_blank_test(String name, String imageUrl, int price) {
        // when + then
        assertThatThrownBy(() -> new Product(name, imageUrl, price))
            .isInstanceOf(NullOrBlankException.class);
    }

    @DisplayName("상품 가격이 음수이면 에러를 발생한다.")
    @Test
    void validate_price_value_is_positive() {
        // when + then
        assertThatThrownBy(() -> new Product("연필", "이미지", -1))
            .isInstanceOf(PriceNotUnderZeroException.class);
    }

}