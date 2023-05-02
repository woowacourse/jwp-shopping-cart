package cart.domain.member;

import java.util.Objects;

public class Member {

    private final Id id;
    private final Username username;
    private final Password password;

    public Member(final long id, final String username, final String password) {
        this.id = new Id(id);
        this.username = new Username(username);
        this.password = new Password(password);
    }

    public Member(final String username, final String password) {
        this.id = null;
        this.username = new Username(username);
        this.password = new Password(password);
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
