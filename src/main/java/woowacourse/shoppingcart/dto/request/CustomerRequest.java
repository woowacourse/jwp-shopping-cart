package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;

public class CustomerRequest {

    @NotBlank(message = "유저 이름은 빈칸일 수 없습니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 빈칸일 수 없습니다.")
    private String password;

    private CustomerRequest() {
    }

    public CustomerRequest(final String userName, final String password) {
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
