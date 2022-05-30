package woowacourse.shoppingcart.domain;

public class Customer {
    private final Long id;
    private final String email;
    private final String username;

    public Customer(String email, String username) {
        this(null, email, username);
    }

    public Customer(Long id, String email, String username) {
        this.id = id;
        this.email = email;
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
}
