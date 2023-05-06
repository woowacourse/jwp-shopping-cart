package cart.dto.request;

public class AuthRequest {

    private static final int EMAIL_INDEX = 0;
    private static final int PASSWORD_INDEX = 1;

    private final String email;
    private final String password;

    public AuthRequest(String[] authValues) {
        this(authValues[EMAIL_INDEX], authValues[PASSWORD_INDEX]);
    }

    public AuthRequest(String email, String password) {
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
