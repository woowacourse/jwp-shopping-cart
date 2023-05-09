package cart.dto;

import cart.domain.member.Member;

public class MemberResponse {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;

    public MemberResponse(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static MemberResponse of(final Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getPassword(), member.getName());
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

    public String getName() {
        return name;
    }
}
