package cart.domain;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

    @ParameterizedTest(name = "상품 이름 길이가 1~25글자 이내면 정상적으로 상품 객체가 생성된다.")
    @ValueSource(strings = {"상", "상품", "상품상품상품상품상품상품상품상품상품상품상품상품상"})
    void create_withName_success(final String validName) {
        final Product createdProduct = assertDoesNotThrow(() ->
                Product.create(validName, "image_url", 10000, ProductCategory.KOREAN));

        assertThat(createdProduct)
                .extracting("name", "imageUrl", "price", "category")
                .contains(validName, "image_url", 10000, ProductCategory.KOREAN);
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

    @ParameterizedTest(name = "상품 가격이 0~10,000,000원 이내면 정상적으로 상품 객체가 생성된다.")
    @ValueSource(ints = {0, 10_000, 10_000_000})
    void create_withPrice_success(final int validPrice) {
        final Product createdProduct = assertDoesNotThrow(() ->
                Product.create("상품", "image_url", validPrice, ProductCategory.KOREAN));

        assertThat(createdProduct)
                .extracting("name", "imageUrl", "price", "category")
                .contains("상품", "image_url", validPrice, ProductCategory.KOREAN);
    }

    @ParameterizedTest(name = "상품 가격이 0원 미만, 10,000,000원 초과면 예외가 발생한다.")
    @ValueSource(ints = {-1, 10_000_001})
    void create_withPrice_fail(final int invalidPrice) {
        assertThatThrownBy(() ->
                Product.create("상품", "image_url", invalidPrice, ProductCategory.KOREAN))
                .isInstanceOf(GlobalException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.PRODUCT_PRICE_RANGE);;
    }
}
