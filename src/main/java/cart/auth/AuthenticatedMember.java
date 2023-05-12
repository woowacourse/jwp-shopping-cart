package cart.auth;

import cart.dto.MemberDto;

public class AuthenticatedMember {
    private final String email;
    private final String password;

    private AuthenticatedMember(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static AuthenticatedMember from(final MemberDto memberDto) {
        return new AuthenticatedMember(memberDto.getEmail(), memberDto.getPassword());
    }

    public static AuthenticatedMember of(final String email, final String password) {
        return new AuthenticatedMember(email, password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
