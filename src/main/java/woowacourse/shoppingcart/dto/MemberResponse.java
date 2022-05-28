package woowacourse.shoppingcart.dto;

public class MemberResponse {

    private String email;
    private String nickname;

    private MemberResponse() {
    }
    public MemberResponse(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }
}
