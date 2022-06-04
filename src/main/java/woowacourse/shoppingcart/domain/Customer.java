package woowacourse.shoppingcart.domain;

public class Customer {

    private final long id;
//    private final Account account;
//    private final Nickname nickname;
//    private final Password password;
//    private final Address address;
//
//
private final String account;
    private final String nickname;
    private final String password;
    private final Address address;
    private final PhoneNumber phoneNumber;

    public Customer(String account, String nickname, String password, Address address, PhoneNumber phoneNumber) {
        this(0, account, nickname, password, address, phoneNumber);
    }

    public Customer(long id, String account, String nickname, String password, Address address, PhoneNumber phoneNumber) {
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
        return address.getValue();
    }

    public String getPhoneNumber() {
        return phoneNumber.getValue();
    }

}
