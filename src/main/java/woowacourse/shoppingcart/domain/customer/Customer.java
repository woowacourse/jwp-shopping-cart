package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class Customer {

    private final Long id;
    private final Email email;
    private Nickname nickname;
    private Password password;

    public Customer(Long id, String email, String nickname, String password) {
        this.id = id;
        this.email = new Email(email);
        this.nickname = new Nickname(nickname);
        this.password = new Password(password);
    }

    public Customer(String email, String nickname, String password) {
        this(null, email, nickname, password);
    }

    public boolean isPasswordMatched(String password) {
        return this.password.equals(new Password(password));
    }

    public void changePassword(String prevPassword, String newPassword) {
        if (!password.equals(new Password(prevPassword))) {
            throw new IllegalArgumentException("이전 패스워드가 틀렸습니다.");
        }
        password = new Password(newPassword);
    }

    public void changeNickname(String nickname) {
        this.nickname = new Nickname(nickname);
    }

    public Long getId() {
        return id;
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
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
