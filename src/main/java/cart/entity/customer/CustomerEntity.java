package cart.entity.customer;

import java.util.Objects;

public final class CustomerEntity {

    private Long id;
    private Email email;
    private String password;

    CustomerEntity() {

    }

    public CustomerEntity(Long id, String email, String password) {
        this.id = id;
        this.email = new Email(email);
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
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
        final CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
