package woowacourse.shoppingcart.customer.domain;

import woowacourse.shoppingcart.customer.support.exception.CustomerException;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;

public class Customer {

    private static final long TEMPORARY_ID = 0;

    private final long id;
    private final Email email;
    private Nickname nickname;
    private Password password;

    public Customer(final long id, final Email email, final Nickname nickname, final Password password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Customer(final Email email, final Nickname nickname, final Password password) {
        this(TEMPORARY_ID, email, nickname, password);
    }

    public Customer(final long id, final String email, final String nickname, final String password) {
        this(id, new Email(email), new Nickname(nickname), new Password(password));
    }

    public Customer(final String email, final String nickname, final String password) {
        this(TEMPORARY_ID, email, nickname, password);
    }

    public void updateProfile(final String nickname) {
        this.nickname = new Nickname(nickname);
    }

    public void updatePassword(final String originPassword, final String newPassword) {
        updatePassword(new Password(originPassword), new Password(newPassword));
    }

    public void updatePassword(final Password originPassword, final Password newPassword) {
        validatePasswordMatch(originPassword);
        this.password = newPassword;
    }

    private void validatePasswordMatch(final Password originPassword) {
        if (!password.equals(originPassword)) {
            throw new CustomerException(CustomerExceptionCode.MISMATCH_PASSWORD);
        }
    }

    public boolean isPasswordDisMatch(final String otherPassword) {
        return !password.equalsValue(otherPassword);
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email.get();
    }

    public String getNickname() {
        return nickname.get();
    }

    public String getPassword() {
        return password.get();
    }
}
