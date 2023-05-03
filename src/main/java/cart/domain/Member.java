package cart.domain;

public class Member {

    private final Long id;
    private final Email email;
    private final Password password;

    public Member(final String email, final String password) {
        this(null, email, password);
    }

    public Member(final Long id, final String email, final String password) {
        this.id = id;
        this.email = Email.from(email);
        this.password = Password.from(password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public void login(final String password) {
        if (!this.password.match(Password.from(password))) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
