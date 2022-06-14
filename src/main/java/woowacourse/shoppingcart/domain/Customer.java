package woowacourse.shoppingcart.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import woowacourse.shoppingcart.exception.LoginFailException;

public class Customer {

    private final long id;
    private final Account account;
    private final Nickname nickname;
    private final EncodedPassword password;
    private final Address address;
    private final PhoneNumber phoneNumber;

    public Customer(final Account account, final Nickname nickname, final EncodedPassword password, final Address address, final PhoneNumber phoneNumber) {
        this(0, account, nickname, password, address, phoneNumber);
    }

    public Customer(final long id, final Account account, final Nickname nickname, final EncodedPassword password, final Address address, final PhoneNumber phoneNumber) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Nickname getNickname() {
        return nickname;
    }

    public EncodedPassword getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public boolean checkPasswordNotMatch(PasswordEncoder passwordEncoder, String password) {
        return this.password.isNotMatch(passwordEncoder, password);
    }
}
