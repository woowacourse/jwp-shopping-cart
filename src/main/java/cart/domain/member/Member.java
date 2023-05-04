package cart.domain.member;

public class Member {

    private final Email email;
    private final Password password;

    public Member(String email, String password) {
        this.email = new Email(email);
        this.password = new Password(password);
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }
}
