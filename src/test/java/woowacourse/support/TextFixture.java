package woowacourse.support;

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
}
