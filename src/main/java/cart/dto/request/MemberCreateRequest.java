package cart.dto.request;

import javax.validation.constraints.Email;

public class MemberCreateRequest {

    @Email
    private String email;
    private String password;

    public MemberCreateRequest() {
    }

    public MemberCreateRequest(final String email, final String password) {
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
