package cart.dto.user;

public class UserResponse {
    private final String email;
    private final String password;

    public UserResponse(String email, String password) {
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
