package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.dto.PhoneNumberFormat;

public class Customer {

    private final long id;
    private final Account account;
    private Nickname nickname;
    private final String password;
    private Address address;
    private PhoneNumber phoneNumber;

    public Customer(Account account, Nickname nickname, String password, Address address, PhoneNumber phoneNumber) {
        this(0, account, nickname, password, address, phoneNumber);
    }

    public Customer(long id, Account account, Nickname nickname, String password, Address address,
                    PhoneNumber phoneNumber) {
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

    public String getAccount() {
        return account.getValue();
    }

    public String getNickname() {
        return nickname.getValue();
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address.getValue();
    }

    public String getPhoneNumber() {
        return phoneNumber.getValue();
    }

    public void updateInformation(String nickname, String address, PhoneNumberFormat phoneNumber) {
        this.nickname = new Nickname(nickname);
        this.address = new Address(address);
        this.phoneNumber = new PhoneNumber(phoneNumber.appendNumbers());
    }
}
