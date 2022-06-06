package woowacourse.utils;

import woowacourse.auth.dto.customer.SignupRequest;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.customer.Customer;

public class Fixture {
    public static final String email = "test@gmail.com";
    public static final String nickname = "testName";
    public static final String password = "a1234!";

    public static final SignupRequest signupRequest = new SignupRequest(email, nickname, password);
    public static final TokenRequest tokenRequest = new TokenRequest(email, password);

    public static final Customer customer = new Customer(1L, email, nickname, password);

    public static final Product 치킨 = new Product(1L, "치킨", 10_000, "http://example.com/chicken.jpg");
    public static final Product 맥주 = new Product(2L, "맥주", 20_000, "http://example.com/beer.jpg");
    public static final Product 초콜렛 = new Product(3L, "초콜렛", 10_000, "www.test.com");
}
