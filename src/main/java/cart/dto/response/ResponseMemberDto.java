package cart.dto.response;

import cart.dao.entity.MemberEntity;

public class ResponseMemberDto {

    private final int id;
    private String email;
    private String password;

    public ResponseMemberDto(int id, String email, String password) {
        this.id = id;
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
