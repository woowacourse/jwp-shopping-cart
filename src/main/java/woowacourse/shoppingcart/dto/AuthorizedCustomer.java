package woowacourse.shoppingcart.dto;

public class AuthorizedCustomer {
    private final Long id;
    private final String username;
    private final String email;
    private final String password;

    public AuthorizedCustomer(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }
}
