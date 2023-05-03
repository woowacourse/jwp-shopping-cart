package cart.presentation.dto;

public class AuthInfo {

    private String email;
    private String password;

    public AuthInfo() {
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
