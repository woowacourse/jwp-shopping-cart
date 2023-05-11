package cart.entity.member;

import java.util.Objects;

public class Member {

    private final Long id;
    private final Email email;
    private final Password password;
    private final Role role;

    public Member(final Long id, final Email email, final Password password, final Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public boolean isAdminUser() {
        return this.role.isAdmin();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(id, member.id) && Objects.equals(email, member.email) && Objects.equals(password,
            member.password) && role == member.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, role);
    }
}
