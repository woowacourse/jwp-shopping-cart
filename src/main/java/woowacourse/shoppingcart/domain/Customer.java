package woowacourse.shoppingcart.domain;

public class Customer {

    private final long id;
    private final String account;
    private final String nickname;
    private final String password;
    private final String address;
    private final String phoneNumber;

    public Customer(String account, String nickname, String password, String address, String phoneNumber) {
        this(0, account, nickname, password, address, phoneNumber);
    }

    public Customer(long id, String account, String nickname, String password, String address, String phoneNumber) {
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

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
