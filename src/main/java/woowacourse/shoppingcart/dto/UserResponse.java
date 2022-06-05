package woowacourse.shoppingcart.dto;

public class UserResponse {
    private String email;
    private String nickname;

    public UserResponse() {
    }

    public UserResponse(String email, String nickname) {
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
