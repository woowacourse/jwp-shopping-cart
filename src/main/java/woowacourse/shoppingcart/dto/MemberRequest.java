package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberRequest {

    @Email
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    private MemberRequest() {
    }

    public MemberRequest(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
