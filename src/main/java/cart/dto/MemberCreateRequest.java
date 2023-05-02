package cart.dto;

import cart.domain.member.entity.Member;
import javax.validation.constraints.Email;

public class MemberCreateRequest {

    @Email
    private final String email;
    private final String password;

    public MemberCreateRequest(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public Member makeMember() {
        return new Member(null, email, password, null, null);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
