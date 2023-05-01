package cart.entity.customer;

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
}
