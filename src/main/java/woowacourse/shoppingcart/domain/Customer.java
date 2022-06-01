package woowacourse.shoppingcart.domain;

public class Customer {

    private final long id;
    private final Account account;
    private final String nickname;
    private final String password;
    private final String address;
    private final String phoneNumber;

    public Customer(final Account account, final String nickname, final String password, final String address, final String phoneNumber) {
        this(0, account, nickname, password, address, phoneNumber);
    }

    public Customer(final long id, final Account account, final String nickname, final String password, final String address, final String phoneNumber) {
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

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
