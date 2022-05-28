package woowacourse.shoppingcart.domain;

import java.util.Objects;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class Customer {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;

    public Customer(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Customer(String username, String email, String password) {
        this(null, username, email, hash(password));
    }

    private static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(username, customer.username) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
