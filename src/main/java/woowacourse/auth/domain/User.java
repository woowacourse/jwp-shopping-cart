package woowacourse.auth.domain;

public class User {

    private final Long id;
    private final String username; // TODO: id vs username as token payload
    private final String password;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getTokenPayload() {
        return id.toString();
    }

    public boolean hasSamePassword(String password) {
        return this.password.equals(password);
    }
}
