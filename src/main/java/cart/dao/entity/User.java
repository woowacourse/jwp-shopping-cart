package cart.dao.entity;

import java.time.LocalDateTime;

public class User {

    private final Long id;
    private final String email;
    private final String password;
    private final LocalDateTime createdAt;

    public User(final Long id, final String email, final String password, final LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
    }

    public void validatePassword(final String password) {
        if (this.password.equals(password)) {
            return;
        }

        throw new IllegalArgumentException("비밀번호가 잘못 되었습니다.");
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
