package cart.dao;

public class AccountEntity {

    private final Long id;
    private final String email;
    private final String password;
    private Long cartId;

    public AccountEntity(final Long id, final String email, final String password, final Long cartId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.cartId = cartId;
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

    public Long getCartId() {
        return cartId;
    }
}
