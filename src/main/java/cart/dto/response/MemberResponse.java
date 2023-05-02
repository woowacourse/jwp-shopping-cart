package cart.dto.response;

import cart.domain.Member;

public class MemberResponse {

    private final String email;
    private final String password;

    public MemberResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public MemberResponse(Member member) {
        this(member.getEmail(), member.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
