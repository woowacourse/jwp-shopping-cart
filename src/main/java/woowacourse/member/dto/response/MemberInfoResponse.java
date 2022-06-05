package woowacourse.member.dto.response;

import woowacourse.member.domain.Member;

public class MemberInfoResponse {

    private final long id;
    private final String email;
    private final String name;

    private MemberInfoResponse(long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public MemberInfoResponse(Member member) {
        this(member.getId(), member.getEmail(), member.getName());
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
