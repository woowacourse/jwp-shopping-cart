package cart.presentation.dto;

import cart.business.domain.MemberEmail;

public class MemberRequest {

    private String email;
    private String password;

    public MemberRequest() {
    }

    public MemberRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private void validateEmail(String email) {
        new MemberEmail(email);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
