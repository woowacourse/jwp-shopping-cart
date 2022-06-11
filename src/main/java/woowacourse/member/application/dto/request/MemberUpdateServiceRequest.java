package woowacourse.member.application.dto.request;

public class MemberUpdateServiceRequest {

    private final String nickname;

    public MemberUpdateServiceRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
