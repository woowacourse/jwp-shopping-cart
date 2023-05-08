package cart.dto.request;

public class CertifiedCustomer {

    private final Long id;
    private final String email;
    private final String password;

    public CertifiedCustomer(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }
}
