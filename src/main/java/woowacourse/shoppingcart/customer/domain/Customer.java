package woowacourse.shoppingcart.customer.domain;

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

    public void update(final String nickname, final String password) {
        this.nickname = new Nickname(nickname);
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
        return email.get();
    }

    public String getNickname() {
        return nickname.get();
    }

    public String getPassword() {
        return password.get();
    }
}
