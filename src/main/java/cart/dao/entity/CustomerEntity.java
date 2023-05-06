package cart.dao.entity;

import cart.domain.Email;
import cart.domain.Password;

public class CustomerEntity {

    private final Long id;
    private final String email;
    private final String password;

    public CustomerEntity(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static CustomerEntity from(final Email email, final Password password) {
        return new Builder()
                .email(email.getEmail())
                .password(password.getPassword())
                .build();
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
