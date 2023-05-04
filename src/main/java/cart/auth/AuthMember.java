package cart.auth;

public class AuthMember {

    private final int id;
    private final String email;
    private final String password;

    public AuthMember(final int id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
