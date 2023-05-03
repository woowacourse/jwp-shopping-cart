package cart.domain.member;

public class Member {

    private final MemberUsername username;
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
