package woowacourse;

import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.customer.Customer;

public class TestFixture {

    public static Customer customer = new Customer(1L, "kun", "kun@email.com", "qwerasdf123");

    public static Product product1 = new Product(1L, "product1", 1000, "url1");
    public static Product product2 = new Product(2L, "product1", 2000, "url2");
}
