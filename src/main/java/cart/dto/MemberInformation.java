package cart.dto;

public class MemberInformation {

    private final String email;
    private final String password;

    public MemberInformation(final String email, final String password) {
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
