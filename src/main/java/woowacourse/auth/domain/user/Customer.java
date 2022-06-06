package woowacourse.auth.domain.user;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import woowacourse.common.exception.InvalidExceptionType;
import woowacourse.common.exception.InvalidRequestException;

public class Customer {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,20}$");
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]{1,10}$");

    private final Long id;
    private final String username;
    private final EncryptedPassword password;
    private final String nickname;
    private final int age;

    public Customer(Long id, String username, EncryptedPassword password, String nickname, int age) {
        validate(username, nickname, age);
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public Customer(String username, EncryptedPassword password, String nickname, int age) {
        this(null, username, password, nickname, age);
    }

    private void validate(String username, String nickname, int age) {
        validateUsername(username);
        validateNickname(nickname);
        validateAge(age);
    }

    private void validateUsername(String username) {
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        if (!matcher.matches()) {
            throw new InvalidRequestException(InvalidExceptionType.USERNAME_FORMAT);
        }
    }

    private void validateNickname(String nickname) {
        Matcher matcher = NICKNAME_PATTERN.matcher(nickname);
        if (!matcher.matches()) {
            throw new InvalidRequestException(InvalidExceptionType.NICKNAME_FORMAT);
        }
    }

    private void validateAge(int age) {
        if (age < 0 || age > 200) {
            throw new InvalidRequestException(InvalidExceptionType.AGE_FORMAT);
        }
    }

    public boolean hasDifferentPassword(String password) {
        return !this.password.hasSamePassword(password);
    }

    public Customer updatePassword(String newPassword) {
        return new Customer(id, username, new Password(newPassword).toEncrypted(), nickname, age);
    }

    public Customer updateNickname(String newNickname) {
        return new Customer(id, username, password, newNickname, age);
    }

    public Customer updateAge(int newAge) {
        return new Customer(id, username, password, nickname, newAge);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password.getValue();
    }

    public EncryptedPassword getEncryptedPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
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
        return age == customer.age
                && Objects.equals(id, customer.id)
                && Objects.equals(username, customer.username)
                && Objects.equals(password, customer.password)
                && Objects.equals(nickname, customer.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, nickname, age);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password=" + password +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                '}';
    }
}
