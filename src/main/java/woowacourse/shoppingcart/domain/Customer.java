package woowacourse.shoppingcart.domain;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import woowacourse.auth.domain.EncryptedPassword;
import woowacourse.auth.domain.Password;

public class Customer {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]{4,20}$");
    private static final Pattern NICKNAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]{1,10}$");

    private final String username;
    private final EncryptedPassword password;
    private final String nickname;
    private final int age;

    public Customer(String username, EncryptedPassword password, String nickname, int age) {
        validate(username, nickname, age);
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public Customer(String username, Password password, String nickname, int age) {
        this(username, password.toEncrypted(), nickname, age);
    }

    private void validate(String username, String nickname, int age) {
        validateUsername(username);
        validateNickname(nickname);
        validateAge(age);
    }

    private void validateUsername(String username) {
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("아이디는 최소 4글자, 최대 20글자여야 합니다.");
        }
    }

    private void validateNickname(String nickname) {
        Matcher matcher = NICKNAME_PATTERN.matcher(nickname);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("닉네임은 최소 1글자, 최대 10글자여야 합니다.");
        }
    }

    private void validateAge(int age) {
        if (age < 0 || age > 200) {
            throw new IllegalArgumentException("나이는 0~200세만 입력할 수 있습니다.");
        }
    }

    public boolean hasSameUsername(String username) {
        return this.username.equals(username);
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
                && Objects.equals(username, customer.username)
                && Objects.equals(password, customer.password)
                && Objects.equals(nickname, customer.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, nickname, age);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", password=" + password +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                '}';
    }
}
