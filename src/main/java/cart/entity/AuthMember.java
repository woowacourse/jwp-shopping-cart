package cart.entity;

public class AuthMember {

    private final String email;
    private final String password;

    public AuthMember(final String email, final String password) {
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
