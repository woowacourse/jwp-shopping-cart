package woowacourse.domain;

public class User {

    private final Long id;
    private final String username;
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
