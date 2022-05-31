package woowacourse.auth.dto.request;

public class MemberUpdateRequest {

    private String nickname;

    public MemberUpdateRequest() {
    }

    public MemberUpdateRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
