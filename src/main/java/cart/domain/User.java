package cart.domain;

import java.util.Objects;

public class User {

    private final Long id;
    private final Email email;
    private final Password password;

    public static class Builder {
        private Long id;
        private Email email;
        private Password password;

        public Builder() {
        }

        public Builder id(Long value) {
            id = value;
            return this;
        }

        public Builder email(Email value) {
            email = value;
            return this;
        }

        public Builder password(Password value) {
            password = value;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder) {
        validate(builder);
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
    }

    private void validate(Builder builder) {
        if (builder.email == null) {
            throw new IllegalArgumentException("이메일은 빈 값일 수 없습니다.");
        }
        if (builder.password == null) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
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
