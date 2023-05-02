package cart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {

    @ParameterizedTest(name = "상품 이름 길이가 1글자 미만, 25글자 초과면 예외가 발생한다.")
    @ValueSource(strings = {"", "상품상품상품상품상품상품상품상품상품상품상품상품상품"})
    void create_withName_fail(final String invalidName) {
        assertThatThrownBy(() ->
            ProductName.create(invalidName))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.PRODUCT_NAME_LENGTH);
    }
}
