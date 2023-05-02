package cart.domain;

public class User {

    private Long id;
    private final String email;
    private final String password;
    private final Cart cart;

    public User(String email, String password, Cart cart) {
        this.email = email;
        this.password = password;
        this.cart = cart;
    }

    public User(Long id, String email, String password, Cart cart) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.cart = cart;
    }
}
