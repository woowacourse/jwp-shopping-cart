package cart.web.settings.dto;

import cart.domain.persistence.entity.MemberEntity;

public class MemberResponse {

    private final String email;
    private final String password;

    public MemberResponse(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberResponse from(final MemberEntity memberEntity) {
        return new MemberResponse(memberEntity.getEmail(), memberEntity.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
