package cart.domain;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

    @ParameterizedTest(name = "정상적인 상품 정보가 들어오면 예외가 상품 객체가 생성된다")
    @CsvSource(value = {"상:0", "상품:10000", "상품상품상품상품상품상품상품상품상품상품상품상품상:10_00_0000"}, delimiter = ':')
    void create_success(final String validName, final int validPrice) {
        final Product createdProduct = assertDoesNotThrow(() ->
                Product.create(validName, "image_url", validPrice, ProductCategory.KOREAN));

        assertThat(createdProduct)
                .extracting("name", "imageUrl", "price", "category")
                .containsExactly(validName, "image_url", validPrice, ProductCategory.KOREAN);
    }

    @ParameterizedTest(name = "상품 이름 길이가 1글자 미만, 25글자 초과면 예외가 발생한다.")
    @ValueSource(strings = {"", "상품상품상품상품상품상품상품상품상품상품상품상품상품"})
    void create_withName_fail(final String invalidName) {
        assertThatThrownBy(() ->
                Product.create(invalidName, "image_url", 10000, ProductCategory.KOREAN))
                .isInstanceOf(GlobalException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.PRODUCT_NAME_LENGTH);
    }

    @ParameterizedTest(name = "상품 가격이 0원 미만, 10,000,000원 초과면 예외가 발생한다.")
    @ValueSource(ints = {-1, 10_000_001})
    void create_withPrice_fail(final int invalidPrice) {
        assertThatThrownBy(() ->
                Product.create("상품", "image_url", invalidPrice, ProductCategory.KOREAN))
                .isInstanceOf(GlobalException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.PRODUCT_PRICE_RANGE);
    }
}
