package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Customer {

    private final Email email;
    private final Nickname nickname;
    private final Password password;

    public Customer(Email email, Nickname nickname, Password password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Customer(String email, String nickname, String password) {
        this(new Email(email), new Nickname(nickname), new Password(password));
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getNickname() {
        return nickname.getValue();
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
        return Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
