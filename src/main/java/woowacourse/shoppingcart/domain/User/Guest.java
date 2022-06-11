package woowacourse.shoppingcart.domain.User;

import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.exception.NotLoginException;

public class Guest implements User {

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public UserName getUserName() {
        throw new NotLoginException();
    }
}
