package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class EditCustomerRequest {

    @Pattern(regexp = "[a-z1-9_]{5,20}", message = "이름은 5~20자에 소문자, 숫자, 언더바(_)만 사용가능합니다😉")
    @NotBlank(message = "이름을 입력해주세요😉")
    private String userName;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*_-]).{8,16}$", message = "비밀번호는 8~16자에 소문자, 대문자, 숫자, 특수문자가 1자 이상씩 들어가야합니다😉")
    @NotBlank(message = "비밀번호를 입력해주세요😉")
    private String password;

    public EditCustomerRequest() {
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
