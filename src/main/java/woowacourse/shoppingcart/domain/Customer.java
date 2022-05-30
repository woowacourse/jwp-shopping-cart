package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Customer {
    private final String name;
    private final String email;
    private final String password;

    public Customer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
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
        return Objects.equals(name, customer.name) && Objects.equals(email, customer.email)
                && Objects.equals(password, customer.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password);
    }
}
