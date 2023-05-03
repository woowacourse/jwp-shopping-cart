package cart.dao.entity;

public class CustomerEntity {

    private final Long id;
    private final String email;
    private final String password;

    public CustomerEntity(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static class Builder {
        private Long id;
        private String email;
        private String password;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public CustomerEntity build() {
            return new CustomerEntity(id, email, password);
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
}
