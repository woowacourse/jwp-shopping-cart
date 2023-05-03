package cart.auth;

import cart.entity.MemberEntity;

public class MemberAuthentication {

    private final String email;
    private final String password;

    private MemberAuthentication(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberAuthentication of(final String email, final String password) {
        return new MemberAuthentication(email, password);
    }

    public static MemberEntity toEntity(final MemberAuthentication memberAuthentication) {
        return MemberEntity.of(memberAuthentication.email, memberAuthentication.password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
