package cart.web.controller.dto.request;

public class AuthorizedUserRequest {
    private String email;
    private String password;

    public AuthorizedUserRequest() {
    }

    public AuthorizedUserRequest(String email, String password) {
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
