package woowacourse.auth.dto;

public class MemberRequest {

    private String email;
    private String password;
    private String nickname;

    public MemberRequest() {
    }

    public MemberRequest(String email, String password, String nickname) {
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
