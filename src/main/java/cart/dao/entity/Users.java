package cart.dao.entity;

import java.time.LocalDateTime;

public class Users {

    private final Long id;
    private final String email;
    private final String password;
    private final LocalDateTime createdAt;

    public Users(final Long id, final String email, final String password, final LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
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
