package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.domain.customer.vo.Account;
import woowacourse.shoppingcart.domain.customer.vo.Address;
import woowacourse.shoppingcart.domain.customer.vo.Nickname;
import woowacourse.shoppingcart.domain.customer.vo.Password;
import woowacourse.shoppingcart.domain.customer.vo.PhoneNumber;

public class Customer {

    private final Long id;
    private final Account account;
    private Nickname nickname;
    private final Password password;
    private Address address;
    private PhoneNumber phoneNumber;

    private Customer(Long id, String account, String nickname, String password, String address,
                     PhoneNumber phoneNumber) {
        this.id = id;
        this.account = new Account(account);
        this.nickname = new Nickname(nickname);
        this.password = new Password(password);
        this.address = new Address(address);
        this.phoneNumber = phoneNumber;
    }

    public Customer(Long id, String account, String nickname, String password, String address, String phoneNumber) {
        this(id, account, nickname, password, address, new PhoneNumber(phoneNumber));
    }

    public Customer(Long id, String account, String nickname, String password, String address,
                    String phoneNumberStart, String phoneNumberMiddle, String phoneNumberLast) {
        this(id, account, nickname, password, address,
                new PhoneNumber(phoneNumberStart, phoneNumberMiddle, phoneNumberLast));
    }

    public void encryptPassword(PasswordEncoder passwordEncoder) {
        password.validateRawPassword();
        password.encrypt(passwordEncoder);
    }

    public void changeNickname(String nickname) {
        this.nickname = new Nickname(nickname);
    }

    public void changeAddress(String address) {
        this.address = new Address(address);
    }

    public void changePhoneNumber(String start, String middle, String last) {
        this.phoneNumber = new PhoneNumber(start, middle, last);
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Nickname getNickname() {
        return nickname;
    }

    public Password getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }
}
