package cart.dto;

import cart.entity.member.Member;

public class MemberResponse {

    private final long id;
    private final String email;
    private final String password;

    public MemberResponse(final long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static MemberResponse from(final Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getPassword()
        );
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
