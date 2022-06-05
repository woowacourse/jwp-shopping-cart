package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Customer {

    private final Long id;
    private final UserName userName;
    private final EncryptPassword password;

    public Customer(final Long id, final UserName userName, final EncryptPassword password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public void validateUserNameChange(final String userName) {
        this.userName.validateChange(userName);
    }

    public boolean matchesPassword(final PasswordEncryptor passwordEncryptor, final String plainPassword) {
        return this.password.matches(passwordEncryptor, plainPassword);
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName.getValue();
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
