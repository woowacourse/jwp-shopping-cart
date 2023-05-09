package cart.service.dto;

public class UserDto {
    private final Long id;
    private final String email;
    private final String password;

    private UserDto(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
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

    public static class Builder {
        private Long id;
        private String email;
        private String password;

        public Builder id(final long id) {
            this.id = id;
            return this;
        }

        public Builder email(final String email) {
            this.email = email;
            return this;
        }

        public Builder password(final String password) {
            this.password = password;
            return this;
        }


        public UserDto build() {
            return new UserDto(this);
        }
    }
}
