package cart.domain.user;

import java.util.Objects;

public class User {

    private Long id;
    private final String email;
    private final String password;
    private Long cartNo;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.cartNo = id;
    }

    public User(Long id, String email, String password, Long cartNo) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.cartNo = cartNo;
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

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getCartNo() {
        return cartNo;
    }
}
