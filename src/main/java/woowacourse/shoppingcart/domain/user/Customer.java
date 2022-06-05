package woowacourse.shoppingcart.domain.user;

public class Customer {

    private final Long id;
    private final Email email;
    private final Password password;
    private final Nickname nickname;

    public Customer(String email, String password, String nickname) {
        this(null, email, password, nickname);
    }

    public Customer(Long id, String email, String password, String nickname) {
        this.id = id;
        this.email = new Email(email);
        this.password = new Password(password);
        this.nickname = new Nickname(nickname);
    }

    public String getEmail() {
        return email.value();
    }

    public String getPassword() {
        return password.value();
    }

    public String getNickname() {
        return nickname.value();
    }

    public Long getId() {
        return id;
    }

    public boolean isValidPassword(Password password) {
        return this.password.equals(password);
    }
}
