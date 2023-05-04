package cart.domain;

public class User {

    private final Long id;
    private final String email;
    private final String password;

    private User(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
    }

    public Long getId() {
        return id;
    }

    public static class Builder {
        private Long id;
        private String email;
        private String password;

        public Builder() {
        }

        public Builder id(Long value) {
            id = value;
            return this;
        }

        public Builder email(String value) {
            email = value;
            return this;
        }

        public Builder password(String value) {
            password = value;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
