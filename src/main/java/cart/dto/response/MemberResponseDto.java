package cart.dto.response;

import cart.entity.MemberEntity;

public class MemberResponseDto {

    private final Long id;
    private final String email;
    private final String password;

    private MemberResponseDto(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static MemberResponseDto from(final MemberEntity member) {
        return new MemberResponseDto(member.getId(), member.getEmail(), member.getPassword());
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
