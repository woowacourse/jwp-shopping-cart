package cart.domain.user;

import java.util.Objects;
import java.util.regex.Pattern;

public class UserEmail {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    private final String email;

    private UserEmail(final String email) {
        validate(email);
        this.email = email;
    }

    private void validate(final String email) {
        if (PATTERN.matcher(email).matches()) {
            return;
        }

        throw new IllegalArgumentException();
    }

    public static UserEmail from(final String email) {
        return new UserEmail(email);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserEmail userEmail = (UserEmail) o;
        return Objects.equals(getEmail(), userEmail.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }

    public String getEmail() {
        return email;
    }
}
