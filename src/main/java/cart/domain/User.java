package cart.domain;

import java.util.Objects;

public class User {

    private final Long id;
    private final Email email;
    private final Password password;

    public User(final Long id, final Email email, final Password password) {
        validateNotNull(email, password);
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(final Email email, final Password password) {
        this(null, email, password);
    }

    private void validateNotNull(final Email email, final Password password) {
        if (email == null) {
            throw new IllegalArgumentException("이메일은 빈 값일 수 없습니다.");
        }
        if (password == null) {
            throw new IllegalArgumentException("비밀번호는 빈 값일 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", password=" + password +
                '}';
    }
}
