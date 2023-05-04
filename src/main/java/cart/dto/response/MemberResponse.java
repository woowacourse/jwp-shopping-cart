package cart.dto.response;

public class MemberResponse {
    private String email;
    private String password;

    public MemberResponse() {
    }

    public MemberResponse(String email, String password) {
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
