package cart.controller.dto.response;

public class MemberResponse {

    private final String email;
    private final String password;

    public MemberResponse(final String email, final String password) {
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
