package cart.domain;

import java.util.List;
import java.util.Objects;

public class User {

    private final String email;
    private final String password;
    private final Cart cart;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.cart = new Cart(List.of());
    }

    public User(String email, String password, Cart cart) {
        this.email = email;
        this.password = password;
        this.cart = cart;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
