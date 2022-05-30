package woowacourse.shoppingcart.domain;

public class Customer {

    private final Long id;
    private final String email;
    private final String password;
    private final String username;

    public Customer(final Long id, final String email, final String password, final String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public static Customer createWithoutId(final String email, final String password, final String username) {
        return new Customer(null, email, password, username);
    }

    public static Customer createWithoutPassword(final Long id, final String email, final String username) {
        return new Customer(id, email, null, username);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
