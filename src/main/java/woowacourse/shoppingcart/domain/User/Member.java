package woowacourse.shoppingcart.domain.User;

import woowacourse.shoppingcart.domain.customer.UserName;

public class Member implements woowacourse.shoppingcart.domain.User.User {

    private final UserName userName;

    public Member(UserName userName) {
        this.userName = userName;
    }

    @Override
    public boolean isLogin() {
        return false;
    }

    @Override
    public UserName getUserName() {
        return userName;
    }
}
