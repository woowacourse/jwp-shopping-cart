package cart.auth;

public class AuthDto {

    private String email;
    private String password;

    public AuthDto() {
    }

    public AuthDto(final String email, final String password) {
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
