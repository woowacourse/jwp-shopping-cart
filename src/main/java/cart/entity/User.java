package cart.entity;

public final class User {
    private static final int MIN_EMAIL_LENGTH = 5;
    private static final int MAX_EMAIL_LENGTH = 30;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 30;

    private final Long id;
    private final String email;
    private final String password;

    public User(final String email, final String password) {
        this(null, email, password);
    }

    public User(final Long id, final String email, final String password) {
        validate(email, password);
        this.id = id;
        this.email = email;
        this.password = password;
    }

    private void validate(final String email, final String password) {
        validateEmail(email);
        validatePassword(password);
    }

    private void validateEmail(final String email) {
        if (email.length() < MIN_EMAIL_LENGTH || MAX_EMAIL_LENGTH < email.length()) {
            throw new IllegalArgumentException(
                    String.format("이메일은 %d ~ %d 길이여야 합니다.", MIN_EMAIL_LENGTH, MAX_EMAIL_LENGTH));
        }

        final long count = email.chars()
                .filter(character -> character == '@')
                .count();
        if (count != 1) {
            throw new IllegalArgumentException("이메일은 '@'를 한 개 포함해야 합니다.");
        }
    }

    private void validatePassword(final String password) {
        if (password.length() < MIN_PASSWORD_LENGTH || MAX_PASSWORD_LENGTH < password.length()) {
            throw new IllegalArgumentException(
                    String.format("비밀번호는 %d ~ %d 길이여야 합니다.", MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH));
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
