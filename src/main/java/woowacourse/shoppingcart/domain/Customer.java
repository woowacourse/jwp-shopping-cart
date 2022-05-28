package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Customer {

    private final Long id;
    private final String username;
    private final String email;

    public Customer(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Customer(String username, String email) {
        this(null, username, email);
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
