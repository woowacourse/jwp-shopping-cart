package woowacourse.member.dto;

import woowacourse.member.domain.Member;

public class MemberResponse {

    private Long id;
    private String email;
    private String name;

    private MemberResponse() {
    }

    public MemberResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static MemberResponse from(final Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getName());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
