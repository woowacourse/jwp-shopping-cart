package woowacourse.shoppingcart.domain.customer;

import static lombok.EqualsAndHashCode.Include;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer {
    @Include
    private Long id;
    private Email email;
    private Nickname nickname;
    private Password password;

    public Customer(Long id, String email, String nickname, String password) {
        this.id = id;
        this.email = Email.of(email);
        this.nickname = Nickname.of(nickname);
        this.password = Password.of(password);
    }

    public Customer(String email, String nickname, String password) {
        this.email = Email.of(email);
        this.nickname = Nickname.of(nickname);
        this.password = Password.of(password);
    }

    public boolean isSamePassword(String password) {
        return this.password.getValue().equals(password);
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

    public Long getId() {
        return id;
    }
}
