package cart.dto;

public class MemberFindResponse {

    private String nickname;
    private String email;
    private String password;

    private MemberFindResponse() {
    }

    public MemberFindResponse(final String nickname, final String email,
                              final String password) {
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
