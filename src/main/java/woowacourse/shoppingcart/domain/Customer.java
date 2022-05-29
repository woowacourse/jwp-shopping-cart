package woowacourse.shoppingcart.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {

    private static final Pattern PATTERN = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");

    private final Long id;
    private final String email;
    private final String password;
    private final String address;
    private final String phoneNumber;

    public Customer(String email, String password, String address, String phoneNumber) {
        this(null, email, password, address, phoneNumber);
    }

    public Customer(Long id, String email, String password, String address, String phoneNumber) {
        this.id = id;
        this.email = Objects.requireNonNull(email, "email은 필수 입력 사항입니다.");
        this.password = Objects.requireNonNull(password, "password는 필수 입력 사항압니다.");
        this.address = Objects.requireNonNull(address, "address는 필수 입력 사항압니다.");
        this.phoneNumber = Objects.requireNonNull(phoneNumber, "phoneNumber는 필수 입력 사항압니다.");
        validateEmail(email);
        validatePassword(password);
    }

    private void validateEmail(String email) {
        Matcher matcher = PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("email 형식을 올바르지 않습니다.");
        }
    }

    private void validatePassword(String password) {
        if (10 > password.length() || password.length() > 20) {
            throw new IllegalArgumentException("password 형식이 올바르지 않습니다. (길이: 10 ~ 20)");
        }
    }
}
