package cart.dto.request;

public class CreateMemberRequest {

    private String email;
    private String password;

    public CreateMemberRequest() {
    }

    public CreateMemberRequest(final String email, final String password) {
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
