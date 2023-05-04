package cart.dto.application;

import cart.dto.request.MemberRequest;

public class MemberDto {
    private final String username;
    private final String password;

    public MemberDto(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public MemberDto(final MemberRequest request) {
        this.username = request.getUsername();
        this.password = request.getPassword();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
