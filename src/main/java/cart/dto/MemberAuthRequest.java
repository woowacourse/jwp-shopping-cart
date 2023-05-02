package cart.dto;

public class MemberAuthRequest {

    private final String email;
    private final String password;

    public MemberAuthRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
