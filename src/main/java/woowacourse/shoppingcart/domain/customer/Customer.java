package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.domain.customer.vo.Account;
import woowacourse.shoppingcart.domain.customer.vo.Address;
import woowacourse.shoppingcart.domain.customer.vo.Nickname;
import woowacourse.shoppingcart.domain.customer.vo.Password;
import woowacourse.shoppingcart.domain.customer.vo.PhoneNumber;

public class Customer {

    private final Account account;
    private final Nickname nickname;
    private final Password password;
    private final Address address;
    private final PhoneNumber phoneNumber;

    public Customer(Account account, Nickname nickname, Password password, Address address,
            PhoneNumber phoneNumber) {
        this.account = account;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String account, String nickname, String password, String address,
            PhoneNumber phoneNumber) {
        this(new Account(account), new Nickname(nickname), new Password(password),
                new Address(address), phoneNumber);
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
