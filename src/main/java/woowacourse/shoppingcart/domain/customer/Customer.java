package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.InvalidArgumentRequestException;

public class Customer {
    private final Username username;
    private final EncodePassword password;
    private final Nickname nickname;
    private final Age age;

    public Customer(Username username, EncodePassword password, Nickname nickname, Age age) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public static Customer of(String username, EncodePassword password, String nickname, int age) {
        return new Customer(
                new Username(username),
                password,
                new Nickname(nickname),
                new Age(age)
        );
    }

    public void validatePassword(EncodePassword password) {
        if (!this.password.hasSamePassword(password)) {
            throw new InvalidArgumentRequestException("비밀번호가 일치하지 않습니다.");
        }
    }

    public Customer updatePassword(EncodePassword newPassword) {
        return new Customer(username, newPassword, nickname, age);
    }

    public String getUsername() {
        return username.getUsername();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public String getNickname() {
        return nickname.getNickname();
    }

    public int getAge() {
        return age.getAge();
    }
}
