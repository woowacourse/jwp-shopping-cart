package cart.domain;

import cart.exception.MemberNotValidException;
import java.time.LocalDateTime;

public class Member {

    private final Long id;
    private final String email;
    private final String password;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Member(final String email, final String password) {
        this(null, email, password, null, null);
    }

    public Member(
            final Long id,
            final String email,
            final String password,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        validate(email, password);
        this.id = id;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private void validate(final String email, final String password) {
        validateEmail(email);
        validatePassword(password);
    }

    private void validateEmail(final String email) {
        if (email == null || email.isBlank()) {
            throw new MemberNotValidException("이메일은 공백일 수 없습니다.");
        }
    }

    private void validatePassword(final String password) {
        if (password == null || password.isBlank()) {
            throw new MemberNotValidException("비밀번호는 공백일 수 없습니다.");
        }
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
