package woowacourse.shoppingcart.domain;

public class Customer {
    private final Long id;
    private final String email;
    private final Password password;
    private final String username;

    public Customer(String email, String password, String username) {
        this(null, email, password, username);
    }

    public Customer(Long id, String email, String password, String username) {
        this.id = id;
        this.email = email;
        this.password = Password.from(password);
        this.username = username;
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
        return password.getPassword();
    }
}
