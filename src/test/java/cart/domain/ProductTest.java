package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductTest {

    @ParameterizedTest(name = "정상적인 상품 정보가 들어오면 예외가 상품 객체가 생성된다")
    @CsvSource(value = {"상:0", "상품:10000", "상품상품상품상품상품상품상품상품상품상품상품상품상:10_00_0000"}, delimiter = ':')
    void create_success(final String validName, final int validPrice) {
        final Product createdProduct = assertDoesNotThrow(() ->
            Product.create(validName, "image_url", validPrice, "KOREAN"));

        assertThat(createdProduct)
            .extracting("name", "imageUrl", "price", "category")
            .containsExactly(validName, "image_url", validPrice, ProductCategory.KOREAN);
    }
}
