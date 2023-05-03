package cart.domain.member;

import javax.validation.Valid;

public class Member {

    @Valid
    private final MemberUsername username;
    @Valid
    private final MemberPassword password;

    public Member(final String username, final String password) {
        this.username = new MemberUsername(username);
        this.password = new MemberPassword(password);
    }


    public String getUsername() {
        return username.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
