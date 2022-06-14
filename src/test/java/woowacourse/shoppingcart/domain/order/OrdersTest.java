package woowacourse.shoppingcart.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.product.Product;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class OrdersTest {

    @Test
    void 주문_총_금액_계산() {
        // given
        Product 사과 = new Product("사과", 1_000, "test");
        Product 오렌지 = new Product("오렌지", 2_000, "test");
        List<OrderDetail> orderDetails = List.of(new OrderDetail(사과, 3), new OrderDetail(오렌지, 2));

        Orders orders = new Orders(1L, orderDetails);

        // when

        // then
        assertThat(orders.calculateTotalCost()).isEqualTo(7_000);
    }
}