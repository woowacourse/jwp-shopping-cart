package cart.auth;

import cart.entity.User;

public final class UserInfo {
    private final String email;
    private final String password;

    public UserInfo(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isCorrect(User user) {
        return email.equals(user.getEmail()) && password.equals(user.getPassword());
    }
}
