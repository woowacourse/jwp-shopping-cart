package woowacourse.shoppingcart.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9_-]{5,20}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");

    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private String address;
    private String phoneNumber;

    public Customer(Long id, Customer customer) {
        this(id, customer.getUsername(), customer.getEmail(), customer.getPassword(), customer.getAddress(),
                customer.getPhoneNumber());
    }

    public Customer(String username, String email, String password, String address, String phoneNumber) {
        this(null, username, email, password, address, phoneNumber);
    }

    public Customer(Long id, String username, String email, String password, String address, String phoneNumber) {
        this.id = id;
        this.username = Objects.requireNonNull(username, "username은 필수 입력 사항입니다.");
        this.email = Objects.requireNonNull(email, "email은 필수 입력 사항입니다.");
        this.password = Objects.requireNonNull(password, "password는 필수 입력 사항압니다.");
        this.address = Objects.requireNonNull(address, "address는 필수 입력 사항압니다.");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber는 필수 입력 사항압니다.");
        validateUsername(username);
        validateEmail(email);
        validatePassword(password);
        validatePhoneNumber(phoneNumber);
    }

    private void validateUsername(String username) {
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("username 형식이 올바르지 않습니다. (영문 소문자, 숫자와 특수기호(_), (-)만 사용 가능");
        }
    }

    private void validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("email 형식이 올바르지 않습니다. (형식: example@email.com");
        }
    }

    private void validatePassword(String password) {
        if (10 > password.length() || password.length() > 20) {
            throw new IllegalArgumentException("password 형식이 올바르지 않습니다. (길이: 10 ~ 20)");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다. (형식: 000-0000-0000");
        }
    }

    public void modify(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
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
