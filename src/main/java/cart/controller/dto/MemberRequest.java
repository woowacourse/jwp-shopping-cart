package cart.controller.dto;

public class MemberRequest {

    private final String email;
    private final String password;

    public MemberRequest(final String email, final String password) {
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
