package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.application.exception.CannotUpdateUserNameException;

public class Customer {

    private final Long id;
    private final String userName;
    private final String password;

    public Customer(final Long id, final String userName, final String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public void validateUserNameChange(final String userName) {
        if (!this.userName.equals(userName)) {
            throw new CannotUpdateUserNameException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
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
