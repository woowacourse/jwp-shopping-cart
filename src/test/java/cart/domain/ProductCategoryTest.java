package cart.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductCategoryTest {

    @ParameterizedTest(name = "주어진 카테고리 정보에 맞는 이름이 들어왔다면, 상품 카테고리 객체를 생성한다.")
    @ValueSource(strings = {"KOREAN", "JAPANESE", "CHINESE", "WESTERN", "SNACK", "DESSERT"})
    void from_success(final String validCategoryName) {
        final ProductCategory category = assertDoesNotThrow(
            () -> ProductCategory.from(validCategoryName));
        assertThat(category)
            .isInstanceOf(ProductCategory.class)
            .extracting("name")
            .isEqualTo(validCategoryName);
    }

    @ParameterizedTest(name = "주어진 카테고리 정보에 맞지 않는 이름이 들어왔다면, 예외가 발생한다.")
    @ValueSource(strings = {"한식", "디저트", "KOREA", "JAPANES", ""})
    void from_fail(final String invalidCategoryName) {
        assertThatThrownBy(() -> ProductCategory.from(invalidCategoryName))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.PRODUCT_INVALID_CATEGORY);

    }
}
