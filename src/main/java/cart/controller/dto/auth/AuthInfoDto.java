package cart.controller.dto.auth;

public class AuthInfoDto {
    private final String email;
    private final String password;

    public AuthInfoDto(String email, String password) {
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
