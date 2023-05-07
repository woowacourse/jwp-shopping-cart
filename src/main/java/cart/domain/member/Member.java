package cart.domain.member;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Member member = (Member) o;
        return Objects.equals(username, member.username) && Objects.equals(password, member.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
