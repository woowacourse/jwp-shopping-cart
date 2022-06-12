package woowacourse.member.application.dto;

public class UpdateNameServiceRequest {

    private final long memberId;
    private final String name;

    public UpdateNameServiceRequest(long memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }
}
