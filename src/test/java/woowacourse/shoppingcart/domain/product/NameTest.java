package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidNameException;

public class NameTest {

    @DisplayName("상품 이름이 빈 값이면 에러를 발생시킨다.")
    @Test
    void EmptyNameException() {
        assertThatThrownBy(() -> new Name(""))
                .isInstanceOf(InvalidNameException.class);
    }
}
