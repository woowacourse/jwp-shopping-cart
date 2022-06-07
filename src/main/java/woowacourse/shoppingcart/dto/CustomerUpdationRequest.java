package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Pattern;

public class CustomerUpdationRequest {

    @Pattern(regexp = "[a-zA-Z0-9가-힣]{2,8}", message = "1000:닉네임 양식이 잘못 되었습니다.")
    private String nickname;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,20}", message = "1000:비밀번호 양식이 잘못 되었습니다.")
    private String password;

    private CustomerUpdationRequest() {
    }

    public CustomerUpdationRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
