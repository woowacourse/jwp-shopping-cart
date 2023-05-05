package cart.auth;

public class Credential {

    private final Long memberId;
    private final String email;
    private final String password;

    public Credential(final String email, final String password) {
        this(null, email, password);
    }

    public Credential(final Long memberId, final String email, final String password) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
