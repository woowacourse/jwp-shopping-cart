package cart.dto.response;

import cart.dto.application.MemberDto;

public class MemberResponse {

    private final String username;
    private final String password;

    public MemberResponse(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public MemberResponse(final MemberDto member) {
        this.username = member.getUsername();
        this.password = member.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
