package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class CustomerRequest {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Length(min = 8, max = 15, message = "비밀번호는 필수 항목입니다.")
    private String password;

    public CustomerRequest() {
    }

    public CustomerRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
