package cart.dto.response;

import cart.entity.MemberEntity;

public class MemberDto {
    private final int id;
    private final String email;
    private final String password;

    public MemberDto(final int id, final String email, final String password) {
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

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
