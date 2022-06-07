package woowacourse.shoppingcart.domain.order;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.product.Product;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class OrderDetailTest {

    @Test
    void 주문_총_금액_계산() {
        // given
        Product 사과 = new Product("사과", 1_000, "test");
        OrderDetail orderDetail = new OrderDetail(사과, 3);

        // when

        // then
        assertThat(orderDetail.calculateCost()).isEqualTo(3_000);
    }
}