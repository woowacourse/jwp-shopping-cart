package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import woowacourse.shoppingcart.exception.bodyexception.InvalidLoginException;

public class Customer {

    private final Long id;
    private final Nickname nickname;
    private final Email email;
    private final Password password;

    public Customer(Long id, String nickname, String email, Password password) {
        this.id = id;
        this.nickname = new Nickname(nickname);
        this.email = new Email(email);
        this.password = password;
    }

    public Customer(Long id, String nickname, String email, String password) {
        this(id, nickname, email, Password.defaultPassword(password));
    }

    public Customer(String nickname, String email, String password) {
        this(null, nickname, email, Password.hashPassword(password));
    }

    public void checkPassword(String value) {
        if (!password.isSamePassword(value)) {
            throw new InvalidLoginException();
        }
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
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
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
