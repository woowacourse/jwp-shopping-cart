package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Customer {

    private final Long id;
    private final UserName userName;
    private final String password;

    public Customer(final Long id, final UserName userName, final String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public void validateUserNameChange(final String userName) {
        this.userName.validateChange(userName);
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName.getValue();
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
        return Objects.equals(id, customer.id) && Objects.equals(userName, customer.userName)
                && Objects.equals(password, customer.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password);
    }
}
