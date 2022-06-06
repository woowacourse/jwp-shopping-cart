package woowacourse.shoppingcart.application;

import woowacourse.shoppingcart.domain.Price;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.ProductRequest;

public class ProductFixture {

    public static final Product 바나나 = new Product(1L, "바나나", new Price(1_000), "banana.com");
    public static final Product 사과 = new Product(2L, "사과", new Price(1_500), "apple.com");
    public static final ProductRequest 바나나_요청 = new ProductRequest("바나나", 1_000, "banana.com");
    public static final ProductRequest 사과_요청 = new ProductRequest("사과", 1_500, "apple.com");

    public static final CartRequest 장바구니_바나나_요청 = new CartRequest(1L);
    public static final CartRequest 장바구니_사과_요청 = new CartRequest(2L);
}
