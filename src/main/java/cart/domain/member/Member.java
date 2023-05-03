package cart.domain.member;

import java.util.Objects;

public class Member {

    private final MemberId id;
    private final MemberUsername username;
    private final MemberPassword password;

    public Member(final long id, final String username, final String password) {
        this.id = new MemberId(id);
        this.username = new MemberUsername(username);
        this.password = new MemberPassword(password);
    }

    public Member(final String username, final String password) {
        this.id = null;
        this.username = new MemberUsername(username);
        this.password = new MemberPassword(password);
    }

    public long getId() {
        return id.getValue();
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
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
