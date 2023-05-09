package cart.domain.member;

import java.util.Objects;

public class MemberEntity {

    private final MemberId id;
    private final Member member;

    public MemberEntity(final long id, final String username, final String password, final boolean isRawPassword) {
        this.id = new MemberId(id);
        this.member = new Member(username, password, isRawPassword);
    }

    public long getId() {
        return id.getValue();
    }

    public String getUsername() {
        return member.getUsername();
    }

    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MemberEntity that = (MemberEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
