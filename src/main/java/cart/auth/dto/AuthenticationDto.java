package cart.auth.dto;

public class AuthenticationDto {

    private final String email;
    private final String password;

    public AuthenticationDto(final String email, final String password) {
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
