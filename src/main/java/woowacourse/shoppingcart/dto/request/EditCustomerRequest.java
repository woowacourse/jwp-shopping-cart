package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;

public class EditCustomerRequest {

    @NotBlank(message = "이름을 입력해주세요😉")
    private String userName;
    @NotBlank(message = "비밀번호를 입력해주세요😉")
    private String password;

    private EditCustomerRequest() {
    }

    public EditCustomerRequest(final String userName, final String password) {
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
