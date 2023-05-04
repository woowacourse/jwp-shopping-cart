package cart.domain.user;

import java.util.Objects;

public class User {

    private final UserId userId;
    private final Email email;
    private final Password password;

    public User(final String email, final String password) {
        this(null, email, password);
    }

    public User(final long userId, final User other) {
        this(new UserId(userId), other.getEmail(), other.getPassword());
    }

    public User(final Long id, final String email, final String password) {
        this(new UserId(id), new Email(email), new Password(password));
    }

    public User(final UserId userId, final Email email, final Password password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public UserId getUserId() {
        return userId;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
