package woowacourse.shoppingcart.domain;

public class Customer {

    private Long id;
    private String userName;
    private String password;

    public Customer(final Long id, final String userName, final String password) {
        this.id = id;
        this.userName = userName;
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
