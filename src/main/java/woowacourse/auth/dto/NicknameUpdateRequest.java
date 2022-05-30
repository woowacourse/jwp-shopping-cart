package woowacourse.auth.dto;

public class NicknameUpdateRequest {

    private String nickname;

    public NicknameUpdateRequest() {
    }

    public NicknameUpdateRequest(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
