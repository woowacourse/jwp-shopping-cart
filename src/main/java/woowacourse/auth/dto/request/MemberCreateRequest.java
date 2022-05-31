package woowacourse.auth.dto.request;

public class MemberCreateRequest {

    private String email;
    private String password;
    private String nickname;

    public MemberCreateRequest() {
    }

    public MemberCreateRequest(String email, String password, String nickname) {
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
