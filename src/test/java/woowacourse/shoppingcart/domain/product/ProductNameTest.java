package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidLengthException;

class ProductNameTest {

    @ParameterizedTest
    @ValueSource(strings = {"",
            " ",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1"})
    @DisplayName("상품의 이름이 길이가 1보다 작거나 100보다 크면 예외를 발생한다.")
    void invalidLengthException(String value) {
        assertThatExceptionOfType(InvalidLengthException.class)
                .isThrownBy(() -> new ProductName(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a",
            "aa",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
    })
    @DisplayName("상품 이름을 생성한다.")
    void createProductName() {
        ProductName productName = new ProductName("a");
        assertThat(productName.getValue()).isEqualTo("a");
    }
}
