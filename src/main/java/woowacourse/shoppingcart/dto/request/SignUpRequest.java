package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;

public class SignUpRequest {

    @NotBlank(message = "이름을 입력해주세요😉")
    private String userName;
    @NotBlank(message = "비밀번호를 입력해주세요😉")
    private String password;

    private SignUpRequest() {
    }

    public SignUpRequest(final String userName, final String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
