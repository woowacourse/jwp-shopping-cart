package cart.domain.member;

public class Member {

    private final Long id;
    private final Email email;
    private final Password password;

    public Member(final Long id, final Email email, final Password password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static Member of(final String email, final String password) {
        return new Member(null, new Email(email), new Password(password));
    }

    public static Member of(final Long id, final String email, final String password) {
        return new Member(id, new Email(email), new Password(password));
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
}
