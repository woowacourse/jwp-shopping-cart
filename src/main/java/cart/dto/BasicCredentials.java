package cart.dto;

public class BasicCredentials {
    private final String email;
    private final String password;

    public BasicCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
