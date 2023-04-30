package cart.dto;

public class MemberRegisterRequest {

    private String nickname;
    private String email;
    private String password;

    private MemberRegisterRequest() {
    }

    public MemberRegisterRequest(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
