package woowacourse.shoppingcart.domain;

import java.util.Optional;

public class Customer {

    private final Optional<Long> id;
    private final Username username;
    private final Password password;
    private final Nickname nickname;
    private final Age age;

    private Customer(Long id, Username username, Password password, Nickname nickname, Age age) {
        this.id = Optional.ofNullable(id);
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public static Customer ofNoId(Username username, Password password, Nickname nickname, Age age) {
        return new Customer(null, username, password, nickname, age);
    }

    public static Customer of(Long id, Username username, Password password, Nickname nickname, Age age) {
        return new Customer(id, username, password, nickname, age);
    }

    public Optional<Long> getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public Nickname getNickname() {
        return nickname;
    }

    public Age getAge() {
        return age;
    }

    public boolean hasSamePassword(Password other) {
        return password.equals(other);
    }
}
