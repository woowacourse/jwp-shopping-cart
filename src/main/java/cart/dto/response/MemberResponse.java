package cart.dto.response;

import cart.domain.Member;

public class MemberResponse {

    private Long id;
    private String email;
    private String password;

    public MemberResponse(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static MemberResponse from(final Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getPassword());
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
