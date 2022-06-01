package woowacourse.shoppingcart.domain;

public class Customer {

    private final Username username;
    private final Password password;
    private final Nickname nickname;
    private final Age age;

    public Customer(Username username, Password password, Nickname nickname, Age age) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
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
