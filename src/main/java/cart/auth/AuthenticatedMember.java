package cart.auth;

import cart.domain.entity.Member;

public class AuthenticatedMember {

    private final long id;
    private final String email;
    private final String password;

    public AuthenticatedMember(final long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static AuthenticatedMember from(final Member member) {
        return new AuthenticatedMember(member.getId(), member.getEmail(), member.getPassword());
    }

    public static AuthenticatedMember of(final long id, final String email, final String password) {
        return new AuthenticatedMember(id, email, password);
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
