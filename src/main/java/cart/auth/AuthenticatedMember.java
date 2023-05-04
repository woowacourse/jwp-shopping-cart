package cart.auth;

import cart.domain.entity.MemberEntity;

public class AuthenticatedMember {

    private final long id;
    private final String email;
    private final String password;

    public AuthenticatedMember(final long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static AuthenticatedMember from(final MemberEntity memberEntity) {
        return new AuthenticatedMember(memberEntity.getId(), memberEntity.getEmail(), memberEntity.getPassword());
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
