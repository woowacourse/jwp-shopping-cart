package cart.dto.request;

public class AuthRequest {

    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    private final String email;
    private final String password;

    public AuthRequest(String[] authValues) {
        this.email = authValues[EMAIL_INDEX];
        this.password = authValues[PASSWORD_INDEX];
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
