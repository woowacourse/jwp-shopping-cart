package cart.domain;

public class User {

    private final Long id;
    private final Email email;
    private final Password password;

    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
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
}
