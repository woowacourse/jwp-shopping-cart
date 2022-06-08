package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.domain.Id;

public class Customer {

    private final Id id;
    private final UserName userName;
    private final Password password;

    public Customer(final UserName userName, final Password password) {
        this.id = new Id(null);
        this.userName = userName;
        this.password = password;
    }

    public Customer(final String userName, final String password) {
        this(null, userName, password);
    }

    public Customer(final Long id, final String userName, final String password) {
        this.id = new Id(id);
        this.userName = new UserName(userName);
        this.password = new Password(password);
    }

    public Long getId() {
        return id.getValue();
    }

    public String getUserName() {
        return userName.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
