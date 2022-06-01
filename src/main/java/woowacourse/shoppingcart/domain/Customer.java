package woowacourse.shoppingcart.domain;

import java.util.Objects;

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

    public Customer updateUsername(Username username) {
        return new Customer(username, password, nickname, age);
    }

    public Customer updatePassword(Password password) {
        return new Customer(username, password, nickname, age);
    }

    public Customer updateNickname(Nickname nickname) {
        return new Customer(username, password, nickname, age);
    }

    public Customer updateAge(Age age) {
        return new Customer(username, password, nickname, age);
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
        return Objects.equals(username, customer.username) && Objects.equals(password,
                customer.password) && Objects.equals(nickname, customer.nickname) && Objects.equals(age,
                customer.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, nickname, age);
    }
}
