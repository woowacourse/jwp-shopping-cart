package woowacourse.shoppingcart.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class Customer {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$");


    private final String email;
    private final Nickname nickname;
    private final String password;

    public Customer(String email, Nickname nickname, String password) {
        validate(email, password);
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Customer(String email, String nickname, String password) {
        this(email, new Nickname(nickname), password);
    }

    private void validate(String email, String password) {
        validateEmailFormat(email);
        validatePasswordFormat(password);
    }

    private void validateEmailFormat(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidCustomerException("잘못된 이메일 형식입니다.");
        }
    }

    private void validatePasswordFormat(String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidCustomerException("잘못된 비밀번호 형식입니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname.getValue();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
