package woowacourse.shoppingcart.domain;

import woowacourse.auth.exception.PasswordNotMatchException;

public class Customer {

    private final Long id;
    private String name;
    private final Email email;
    private EncodedPassword password;

    public Customer(final Long id, final String name, final Email email, final EncodedPassword password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Customer(final String name, final Email email, final EncodedPassword password) {
        this(null, name, email, password);
    }

    public Customer updateName(final String newName) {
        this.name = newName;
        return this;
    }

    public Customer updatePassword(final EncodedPassword newPassword) {
        this.password = newPassword;
        return this;
    }

    public void checkPasswordMatch(final String password) {
        if (!this.password.isSamePassword(password)) {
            throw new PasswordNotMatchException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public EncodedPassword getPassword() {
        return password;
    }
}
