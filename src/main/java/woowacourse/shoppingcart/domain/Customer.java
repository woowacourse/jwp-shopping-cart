package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.auth.domain.EncryptedPassword;
import woowacourse.auth.domain.Password;

public class Customer {

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
        if (username.length() < 4 || username.length() > 20) {
            throw new IllegalArgumentException("아이디는 최소 4글자, 최대 20글자여야 합니다.");
        }
    }

    private void validateNickname(String nickname) {
        if (nickname.isBlank() || nickname.length() > 10) {
            throw new IllegalArgumentException("닉네임은 최소 1글자, 최대 10글자여야 합니다.");
        }
    }

    private void validateAge(int age) {
        if (age < 1) {
            throw new IllegalArgumentException("나이는 최소 1살이어야 합니다.");
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
