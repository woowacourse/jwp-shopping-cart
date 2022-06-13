package woowacourse.helper.fixture;

import static woowacourse.helper.fixture.ProductFixture.PRODUCT_IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_NAME;
import static woowacourse.helper.fixture.ProductFixture.PRODUCT_PRICE;

import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

public class CartFixture {

    public static Cart getFirstCart() {
        return new Cart(1L, 1L, new Product(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_IMAGE), 1);
    }

    public static Cart getSecondCart() {
        return new Cart(2L, 1L, new Product(2L, "치킨", 15000, "chicken.com"), 1);
    }

    public static Cart getThirdCart() {
        return new Cart(3L, 2L, new Product(3L, "맥주", 3000, "beer.com"), 1);
    }
}
