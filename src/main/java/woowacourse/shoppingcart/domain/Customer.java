package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Customer {

    private final Long id;
    private final Username username;
    private final Password password;
    private final Nickname nickname;
    private final Age age;

    private Customer(Long id, Username username, Password password, Nickname nickname, Age age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public static Customer ofNoId(Username username, Password password, Nickname nickname, Age age) {
        return Customer.of(null, username, password, nickname, age);
    }

    public static Customer of(Long id, Username username, Password password, Nickname nickname, Age age) {
        return new Customer(id, username, password, nickname, age);
    }

    public Long getId() {
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

    public Customer updateId(Long id) {
        return new Customer(id, username, password, nickname, age);
    }

    public Customer updatePassword(Password password) {
        return new Customer(id, username, password, nickname, age);
    }

    public Customer updateNickname(Nickname nickname) {
        return new Customer(id, username, password, nickname, age);
    }

    public Customer updateAge(Age age) {
        return new Customer(id, username, password, nickname, age);
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
        return Objects.equals(id, customer.id) && Objects.equals(username, customer.username)
                && Objects.equals(password, customer.password) && Objects.equals(nickname,
                customer.nickname) && Objects.equals(age, customer.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, nickname, age);
    }
}
