package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.datanotmatch.CustomerDataNotMatchException;

public class Customer {

    private final Long id;
    private final String userId;
    private final String nickname;
    private final String password;

    public Customer(final String userId, final String nickname, final String password) {
        this(null, userId, nickname, password);
    }

    public Customer(final Long id, final String userId, final String nickname, final String password) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
    }

    public void comparePasswordFrom(final String password) {
        if (!this.password.equals(password)) {
            throw new CustomerDataNotMatchException("입력한 비밀번호가 올바르지 않습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
