package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidCustomerException;

import java.util.Objects;

public class Customer {
    private static final String DIFFERENT_PASSWORD = "[ERROR] 비밀번호가 일치하지 않습니다.";
    private static final NameValidation usernameValidation = new UserNameValidationImpl();

    private Id id;

    private Name username;
    private Email email;
    private Password password;

    public Customer(String password) {
        this.password = new Password(password);
    }

    public Customer(String email, String password) {
        this(password);
        this.email = new Email(email);
    }

    public Customer(String username, String email, String password) {
        this(email, password);
        usernameValidation.validateName(username);
        this.username = new Name(username);
    }

    public Customer(Long id, String username, String email, String password) {
        this(username, email, password);
        this.id = Id.from(id, "사용자");
    }

    public String generateEncodedPassword() {
        return password.generateEncodedPassword();
    }

    public void validateSamePassword(Customer other) {
        if (!password.isSamePassword(other.password)) {
            throw new InvalidCustomerException(DIFFERENT_PASSWORD);
        };
    }

    public Customer changePassword(String password) {
        return new Customer(username.getName(), email.getEmail(), password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(username, customer.username) && Objects.equals(email, customer.email) && Objects.equals(password, customer.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password);
    }

    public String getUsername() {
        return username.getName();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }
}
