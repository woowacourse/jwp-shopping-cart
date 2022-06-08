package woowacourse.shoppingcart.application;

import java.util.List;
import woowacourse.shoppingcart.domain.product.ImageUrl;
import woowacourse.shoppingcart.domain.product.Name;
import woowacourse.shoppingcart.domain.product.Price;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.OrderRequest;
import woowacourse.shoppingcart.dto.OrdersRequest;
import woowacourse.shoppingcart.dto.ProductRequest;

public class ProductFixture {

    public static final Product 바나나 = new Product(1L, new Name("바나나"), new Price(1_000), new ImageUrl("banana.com"));
    public static final Product 사과 = new Product(2L, new Name("사과"), new Price(1_500), new ImageUrl("apple.com"));
    public static final ProductRequest 바나나_요청 = new ProductRequest("바나나", 1_000, "banana.com");
    public static final ProductRequest 사과_요청 = new ProductRequest("사과", 1_500, "apple.com");

    public static final CartRequest 장바구니_바나나_요청 = new CartRequest(1L);
    public static final CartRequest 장바구니_사과_요청 = new CartRequest(2L);
    public static final OrderRequest 바나나_주문 = new OrderRequest(1L, 10);
    public static final OrderRequest 사과_주문 = new OrderRequest(2L, 20);
    public static final OrdersRequest 바나나_사과_주문_요청 = new OrdersRequest(List.of(바나나_주문, 사과_주문));
    public static final OrdersRequest 바나나_주문_요청 = new OrdersRequest(List.of(바나나_주문));
}
