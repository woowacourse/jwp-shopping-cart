package woowacourse.shoppingcart.domain;

public class Customer {

    private static final int NICKNAME_MINIMUM_LENGTH = 2;
    private static final int NICKNAME_MAXIMUM_LENGTH = 10;
    private static final int ADDRESS_MAXIMUM_LENGTH = 255;
    private static final int PHONE_NUMBER_LENGTH = 11;

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
        validateNickname(nickname);
        validatePassword(password);
        validateAddress(address);
        validatePhoneNumber(phoneNumber);
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임은 비어있을 수 없습니다.");
        }
        if (nickname.length() < NICKNAME_MINIMUM_LENGTH || nickname.length() > NICKNAME_MAXIMUM_LENGTH) {
            throw new IllegalArgumentException("닉네임 길이는 " + NICKNAME_MINIMUM_LENGTH + "~" + NICKNAME_MAXIMUM_LENGTH + "자 이어야 합니다.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 비어있을 수 없습니다.");
        }
    }

    private void validateAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("주소는 비어있을 수 없습니다.");
        }
        if (address.length() > ADDRESS_MAXIMUM_LENGTH) {
            throw new IllegalArgumentException("주소는 " + ADDRESS_MAXIMUM_LENGTH + "자를 초과할 수 없습니다.");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("핸드폰 번호는 비어있을 수 없습니다.");
        }
        if (phoneNumber.length() != PHONE_NUMBER_LENGTH) {
            throw new IllegalArgumentException("핸드폰 번호 길이는 " + PHONE_NUMBER_LENGTH + "자 이어야 합니다.");
        }
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
