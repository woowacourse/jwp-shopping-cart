package cart.dto.request;

public class MemberRequest {

    private final String username;
    private final String password;

    public MemberRequest(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
