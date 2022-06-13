package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidInformationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PriceTest {

    @Test
    void 가격이_자연수가_아닌_경우() {
        assertThatThrownBy(() -> new Price(0)).isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 가격은 자연수이어야 합니다.");
    }
}
