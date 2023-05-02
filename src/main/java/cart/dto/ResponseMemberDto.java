package cart.dto;

import cart.dao.entity.MemberEntity;

public class ResponseMemberDto {

    private final Long id;
    private final String email;
    private final String password;

    public ResponseMemberDto(final MemberEntity memberEntity) {
        this.id = memberEntity.getId();
        this.email = memberEntity.getEmail();
        this.password = memberEntity.getPassword();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
