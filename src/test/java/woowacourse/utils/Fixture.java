package woowacourse.utils;

import woowacourse.auth.dto.customer.SignupRequest;
import woowacourse.auth.dto.token.TokenRequest;
import woowacourse.shoppingcart.domain.customer.Customer;

public class Fixture {
    public static final String email = "test@gmail.com";
    public static final String nickname = "testName";
    public static final String password = "a1234!";

    public static final SignupRequest signupRequest = new SignupRequest(email, nickname, password);
    public static final TokenRequest tokenRequest = new TokenRequest(email, password);

    public static final Customer customer = new Customer(1L, email, nickname, password);
}
