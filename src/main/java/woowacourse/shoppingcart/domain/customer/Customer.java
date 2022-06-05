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

    public Customer(Long id, Account account, Nickname nickname, Password password, Address address,
                    PhoneNumber phoneNumber) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public boolean isNotSamePassword(String password) {
        return this.password.notSameRawValue(password);
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
