package cart.dto.auth;

import cart.dao.entity.MemberEntity;
import cart.dto.response.ResponseMemberDto;

public class AuthInfo {

    private String email;
    private String password;

    public AuthInfo(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static ResponseMemberDto transferEntityToDto(final MemberEntity memberEntity) {
        return new ResponseMemberDto(memberEntity.getId(), memberEntity.getEmail(), memberEntity.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
