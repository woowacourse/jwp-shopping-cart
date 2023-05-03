package cart.dto.response;

import cart.entity.MemberEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public final class MemberResponseDto {

    @Schema(description = "멤버 ID")
    private final Long id;
    @Schema(description = "멤버 이메일")
    private final String email;
    @Schema(description = "멤버 비밀번호")
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
