package cart.domain.member.dto;

public class MemberRequest {
    private final String email;
    private final String password;

    public MemberRequest(String email, String password) {
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
