package cart.dto;

import cart.domain.User;

public class UserCredentialResponse {
    private String email;
    private String password;

    public UserCredentialResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentialResponse from(User user) {
        return new UserCredentialResponse(user.getEmail(), user.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
