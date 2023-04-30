package cart.domain;

public class Member {

    private final Long id;
    private final String email;
    private final String password;

    public Member(final String email, final String password) {
        this(null, email, password);
    }

    public Member(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void login(final String password) {
        if (!this.password.equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
