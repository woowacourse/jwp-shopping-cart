package woowacourse.shoppingcart.domain;

import java.util.Objects;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Customer {

    private final Long id;
    private final String nickname;
    private final String email;
    private final String password;

    public Customer(final Long id, final String nickname, final String email, final String password) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public Customer(final String nickname, final String email, final String password) {
        this(null, nickname, email, hash(password));
    }

    private static String hash(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Customer customer = (Customer) o;
        return Objects.equals(nickname, customer.nickname) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, email);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
