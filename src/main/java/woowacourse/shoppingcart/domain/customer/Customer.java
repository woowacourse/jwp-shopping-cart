package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final UserName userName;
    private final Password password;

    public Customer(final UserName userName, final Password password) {
        this.id = null;
        this.userName = userName;
        this.password = password;
    }

    public Customer(final String userName, final String password) {
        this(null, userName, password);
    }

    public Customer(final Long id, final String userName, final String password) {
        this.id = id;
        this.userName = new UserName(userName);
        this.password = new Password(password);
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
