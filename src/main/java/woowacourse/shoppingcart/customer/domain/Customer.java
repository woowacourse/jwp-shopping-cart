package woowacourse.shoppingcart.customer.domain;

import java.util.Objects;

public class Customer {

    private final Long id;
    private final Nickname nickname;
    private final Email email;
    private final Password password;

    private Customer(final Long id, final String nickname, final String email, final Password password) {
        this.id = id;
        this.nickname = new Nickname(nickname);
        this.email = new Email(email);
        this.password = password;
    }

    public Customer(final Long id, final String nickname, final String email, final String password) {
        this(id, nickname, email, Password.fromHash(password));
    }

    public Customer(final String nickname, final String email, final String password) {
        this(null, nickname, email, Password.fromPlain(password));
    }

    public boolean isSamePassword(final String plainPassword) {
        return password.isSame(plainPassword);
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Customer customer = (Customer) o;
        return Objects.equals(nickname, customer.nickname) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, email);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", nickname=" + nickname +
                ", email=" + email +
                ", password=" + password +
                '}';
    }
}
