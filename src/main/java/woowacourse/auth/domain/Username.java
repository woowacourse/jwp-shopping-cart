package woowacourse.auth.domain;

public class Username {

    private final String username;

    public Username(String username) {
        validate(username);
        this.username = username;
    }

    private void validate(String username) {
        if (username == null || username.isBlank() || username.length() > 32) {
            throw new IllegalArgumentException("유저 이름이 잘못되었습니다.");
        }
    }

    public String getUsername() {
        return username;
    }
}
