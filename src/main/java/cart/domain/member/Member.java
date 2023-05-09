package cart.domain.member;

public class Member {
    private final Email email;
    private final Password password;

    public Member(Email email, Password password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }
}
