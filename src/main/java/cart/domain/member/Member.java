package cart.domain.member;

public class Member {

    private final Email email;
    private final Password password;

    public Member(final String email, final String password) {
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
