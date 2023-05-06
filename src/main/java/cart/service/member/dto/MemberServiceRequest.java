package cart.service.member.dto;

public class MemberServiceRequest {

    private final String email;
    private final String password;

    public MemberServiceRequest(String email, String password) {
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
