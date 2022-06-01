package woowacourse.shoppingcart.domain;

public class Customer {

    private final Long id;
    private final String userName;
    private final String password;

    public Customer(final String name, final String password) {
        this(null, name, password);
    }

    public Customer(final Long id, final String name, final String password) {
        this.id = id;
        this.userName = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
