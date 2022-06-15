package woowacourse.helper.fixture;

import woowacourse.shoppingcart.domain.OrderDetail;
import woowacourse.shoppingcart.domain.Product;

public class OrderFixture {

    public static OrderDetail createProduct(Product product, int quantity) {
        return new OrderDetail(product, quantity);
    }
}
