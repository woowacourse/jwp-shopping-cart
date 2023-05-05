package cart.dto;

import cart.entity.Member;

public class MemberResponse {
    private final Long id;
    private final String email;
    private final String password;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.password = member.getPassword();
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
