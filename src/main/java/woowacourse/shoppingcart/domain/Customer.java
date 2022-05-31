package woowacourse.shoppingcart.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class Customer {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[ㄱ-ㅎ가-힣]{1,5}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$");


    private final String email;
    private final String nickname;
    private final String password;

    public Customer(String email, String nickname, String password) {
        validate(email, nickname, password);
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    private void validate(String email, String nickname, String password) {
        validateEmailFormat(email);
        validateNicknameFormat(nickname);
        validatePasswordFormat(password);
    }

    private void validateEmailFormat(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidCustomerException("잘못된 이메일 형식입니다.");
        }
    }

    private void validateNicknameFormat(String nickname) {
        if (!NICKNAME_PATTERN.matcher(nickname).matches()) {
            throw new InvalidCustomerException("잘못된 닉네임 형식입니다.");
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
        return nickname;
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
