package cart.service.dto;

public class AccountSearchResponse {

    private final Long id;
    private final String email;
    private final String password;

    public AccountSearchResponse(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
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