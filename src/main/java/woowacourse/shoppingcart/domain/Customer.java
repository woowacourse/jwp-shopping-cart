package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Customer {
    private final String username;
    private final String email;
    private final String password;

    public Customer(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
        return Objects.equals(username, customer.username) && Objects.equals(email, customer.email)
                && Objects.equals(password, customer.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
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
}
