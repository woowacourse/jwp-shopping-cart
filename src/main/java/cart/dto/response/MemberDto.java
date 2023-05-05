package cart.dto.response;

import cart.entity.MemberEntity;

public class MemberDto {
    private final Integer id;
    private final String email;
    private final String password;

    public MemberDto(final Integer id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static MemberDto from(MemberEntity member) {
        return new MemberDto(
                member.getId(),
                member.getEmail(),
                member.getPassword()
        );
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
