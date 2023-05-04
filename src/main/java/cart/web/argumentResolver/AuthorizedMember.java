package cart.web.argumentResolver;

public class AuthorizedMember {

    private final long memberId;
    private final String email;
    private final String password;

    public AuthorizedMember(final long memberId, final String email, final String password) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
