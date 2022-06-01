package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");

    private final Long id;
    private final Username username;
    private final Email email;
    private final String password;
    private String address;
    private String phoneNumber;

    public Customer(Long id, Customer customer) {
        this(id, new Username(customer.getUsername()), new Email(customer.getEmail()), customer.getPassword(),
                customer.getAddress(),
                customer.getPhoneNumber());
    }

    public Customer(String username, String email, String password, String address, String phoneNumber) {
        this(null, new Username(username), new Email(email), password, address, phoneNumber);
    }

    public Customer(Long id, String username, String email, String password, String address, String phoneNumber) {
        this(id, new Username(username), new Email(email), password, address, phoneNumber);
    }

    public Customer(Long id, Username username, Email email, String password, String address, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = Objects.requireNonNull(password, "password는 필수 입력 사항압니다.");
        this.address = Objects.requireNonNull(address, "address는 필수 입력 사항압니다.");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber는 필수 입력 사항압니다.");
        validateAddress(address);
        validatePhoneNumber(phoneNumber);
    }

    private void validateAddress(String address) {
        if (address.length() > 255) {
            throw new IllegalArgumentException("address 형식이 올바르지 않습니다. (길이: 255 이하)");
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다. (형식: 000-0000-0000)");
        }
    }

    public void modify(String address, String phoneNumber) {
        this.address = Objects.requireNonNull(address, "address는 필수 입력 사항압니다.");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber는 필수 입력 사항압니다.");
        validateAddress(address);
        validatePhoneNumber(phoneNumber);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username.getValue();
    }

    public String getEmail() {
        return email.getValue();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer)o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
