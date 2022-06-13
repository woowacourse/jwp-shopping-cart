package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidProductException;

public class ProductTest {

    @DisplayName("제품 가격이 범위에 맞지 않을 경우 예외가 발생한다.")
    @Test
    void validateRightPrice() {
        assertThatThrownBy(() -> new Product("김치", -1, "image.com"))
                .isInstanceOf(InvalidProductException.class);
    }
}
