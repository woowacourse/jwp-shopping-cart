package cart.dto;

import cart.domain.MemberEntity;

public class ResponseMemberDto {

    private final Long id;
    private final String email;
    private final String password;

    public ResponseMemberDto(final MemberEntity memberEntity) {
        this.id = memberEntity.getId();
        this.email = memberEntity.getEmailAddress();
        this.password = memberEntity.getPasswordValue();
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
