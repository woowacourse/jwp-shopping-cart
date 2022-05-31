package woowacourse.shoppingcart.domain;

public class Customer {

    private final Long id;
    private final String email;
    private String userName;
    private String password;

    public Customer(final Long id, final String email, final String userName, final String password) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public Customer(final String email, final String userName, final String password) {
        this(null, email, userName, password);
    }

    public void update(final String userName, final String password) {
        this.userName = userName;
        this.password = password;
    }

    public boolean equalsPassword(final String password) {
        return password.equals(this.password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
