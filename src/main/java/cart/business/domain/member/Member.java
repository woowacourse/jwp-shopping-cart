package cart.business.domain.member;

import java.util.Objects;

public class Member {

    private final MemberId id;
    private final MemberEmail email;
    private final MemberPassword password;

    public Member(MemberId id, MemberEmail email, MemberPassword password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return id.equals(member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
