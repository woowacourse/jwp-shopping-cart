package cart.domain.member.entity;

import java.time.LocalDateTime;
import org.springframework.lang.Nullable;

public class Member {

    private final Long id;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private String password;

    public Member(@Nullable final Long id, final String email, final String password,
        @Nullable final LocalDateTime createdAt, @Nullable final LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public boolean passwordNotEquals(final String comparedPassword) {
        return !this.password.equals(comparedPassword);
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

    public void setPassword(final String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
