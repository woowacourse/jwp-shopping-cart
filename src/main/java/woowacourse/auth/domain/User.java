package woowacourse.auth.domain;

import java.util.Objects;

public class User {

    private final String username;
    private final EncryptedPassword password;

    public User(String username, EncryptedPassword password) {
        this.username = username;
        this.password = password;
    }

    public String getTokenPayload() {
        return username;
    }

    public boolean hasDifferentPassword(String password) {
        EncryptedPassword targetPassword = new Password(password).toEncrypted();
        return !this.password.equals(targetPassword);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(username, user.username)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "User{" + "username='" + username + '\'' + ", password=" + password + '}';
    }
}
