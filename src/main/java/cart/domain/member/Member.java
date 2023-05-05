package cart.domain.member;

import cart.domain.Id;
import java.util.Objects;

public class Member {

    private final Id id;
    private final MemberName name;
    private final MemberEmail email;
    private final MemberPassword password;

    public Member(Id id, MemberName name, MemberEmail email, MemberPassword password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id.getId();
    }

    public String getName() {
        return name.getName();
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
