package woowacourse.shoppingcart.domain.User;

import woowacourse.shoppingcart.domain.customer.UserName;

public interface User {

    boolean isLogin();

    UserName getUserName();
}
