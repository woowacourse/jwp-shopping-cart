package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class CustomerDeleteRequest {

    @NotBlank(message = "비밀번호는 필수항목입니다.")
    @Length(min = 8, max = 15, message = "비밀번호 길이는 최소 8자리, 최대 15자리여야 합니다.")
    private String password;

    public CustomerDeleteRequest() {
    }

    public CustomerDeleteRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
