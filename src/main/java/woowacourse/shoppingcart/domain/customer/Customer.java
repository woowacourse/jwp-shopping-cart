package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final String email;
    private final String username;
    private final String password;

    public Customer(String email, String username, String password) {
        this(null, email, username, password);
    }

    public Customer(Long id, String email, String username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
