package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final Email email;
    private Nickname nickname;
    private Password password;

    public Customer(final Long id, final Email email, final Nickname nickname, final Password password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public Customer(final Long id, final String email, final String nickname, final String password) {
        this(id, new Email(email), new Nickname(nickname), new Password(password));
    }

    public Customer(final String email, final String nickname, final String password) {
        this(null, email, nickname, password);
    }

    public void updateNickname(final String nickname) {
        this.nickname = new Nickname(nickname);
    }

    public void updatePassword(final String password) {
        this.password = new Password(password);
    }

    public boolean equalsPassword(final String password) {
        final Password other = new Password(password);
        return other.equals(this.password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getNickname() {
        return nickname.getNickname();
    }

    public String getPassword() {
        return password.getPassword();
    }
}
