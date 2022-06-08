package woowacourse.member.application.dto.request;

public class MemberCreateServiceRequest {

    private final String email;
    private final String password;
    private final String nickname;

    public MemberCreateServiceRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
