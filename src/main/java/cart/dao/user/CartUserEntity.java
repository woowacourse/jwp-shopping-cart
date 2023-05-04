package cart.dao.user;

import cart.domain.user.CartUser;
import cart.domain.user.UserEmail;
import java.util.Objects;

public class CartUserEntity {
    private final Long id;
    private final String email;
    private final String cartPassword;

    public CartUserEntity(final Long id, final String email, final String cartPassword) {
        this.id = id;
        this.email = email;
        this.cartPassword = cartPassword;
    }

    public CartUserEntity(final String email, final String cartPassword) {
        this.id = null;
        this.email = email;
        this.cartPassword = cartPassword;
    }

    public CartUser toCartUser() {
        return new CartUser(
                UserEmail.from(email),
                cartPassword
        );
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CartUserEntity that = (CartUserEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCartPassword() {
        return cartPassword;
    }
}
