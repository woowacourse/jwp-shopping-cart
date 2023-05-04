package cart.controller.dto;

public class AuthInfo {

    private String email;

    private String password;

    private AuthInfo() {
    }

    public AuthInfo(String email, String password) {
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
