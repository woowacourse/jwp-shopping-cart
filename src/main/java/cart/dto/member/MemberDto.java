package cart.dto.member;

import cart.entity.MemberEntity;

public class MemberDto {
    private final Long memberId;
    private final String email;
    private final String password;

    public MemberDto(Long memberId, String email, String password) {
        this.memberId = memberId;
        this.email = email;
        this.password = password;
    }

    public static MemberDto fromEntity(MemberEntity entity) {
        return new MemberDto(entity.getId(), entity.getEmail(), entity.getPassword());
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
