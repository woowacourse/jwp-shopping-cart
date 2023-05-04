package cart.dto.request;

import cart.domain.Member;

public class CreateMemberRequest {

    private String email;
    private String password;

    public CreateMemberRequest() {
    }

    public CreateMemberRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public Member toMember() {
        return new Member(email, password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
