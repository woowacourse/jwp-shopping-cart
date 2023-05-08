package cart.service.dto;

public class MemberInfo {

    private final String email;
    private final String password;

    public MemberInfo(final String email, final String password) {
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
