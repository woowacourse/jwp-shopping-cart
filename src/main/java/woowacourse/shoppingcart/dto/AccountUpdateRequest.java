package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Pattern;

public class AccountUpdateRequest {

    @Pattern(regexp = "^[가-힣A-Za-z0-9]{2,8}$", message = "잘못된 닉네임 형식입니다.")
    private String nickname;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,20}$", message = "잘못된 비밀번호 형식입니다.")
    private String password;

    public AccountUpdateRequest() {
    }

    public AccountUpdateRequest(String nickname, String password) {
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
