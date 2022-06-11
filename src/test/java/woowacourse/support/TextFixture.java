package woowacourse.support;

import woowacourse.shoppingcart.auth.support.jwt.JwtTokenProvider;
import woowacourse.shoppingcart.customer.domain.Email;
import woowacourse.shoppingcart.customer.domain.Nickname;
import woowacourse.shoppingcart.customer.domain.Password;

public class TextFixture {

    public static final String EMAIL_VALUE = "guest@woowa.com";
    public static final Email EMAIL = new Email(EMAIL_VALUE);

    public static final String NICKNAME_VALUE = "guest";
    public static final Nickname NICKNAME = new Nickname(NICKNAME_VALUE);

    public static final String PASSWORD_VALUE = "qwer1234!@#$";
    public static final Password PASSWORD = new Password(PASSWORD_VALUE);

    public static final JwtTokenProvider JWT_TOKEN_PROVIDER = new JwtTokenProvider(
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9eyJzdWIiOiIiLCJuYW1lIjoiSm9obiBEb2UiLCJ", 36000);

    public static final String PRODUCT_NAME1 = "상품1";
    public static final long PRODUCT_PRICE1 = 1000;
    public static final String PRODUCT_IMAGE_URL1 = "http://img1.com";

    public static final String PRODUCT_NAME2 = "상품2";
    public static final long PRODUCT_PRICE2 = 2000;
    public static final String PRODUCT_IMAGE_URL2 = "http://img2.com";
}
