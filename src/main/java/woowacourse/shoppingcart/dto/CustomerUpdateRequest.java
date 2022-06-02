package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Pattern;

public class CustomerUpdateRequest {

    @Pattern(regexp = "^[가-힣A-Za-z0-9]{2,8}$")
    private String nickname;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,20}$")
    private String password;

    public CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(String nickname, String password) {
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
