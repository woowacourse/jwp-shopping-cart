package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerRequest {

    @NotBlank(message = "유저 이름의 길이는 5이상 20이하여야 합니다. : ${validatedValue}")
    @Size(min = 5, max = 20, message = "유저 이름의 길이는 5이상 20이하여야 합니다. : ${validatedValue}")
    private String userName;


    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*_\\-])(?=\\S+$).{8,16}",
            message = "비밀번호는 8~16자의 영문 대문자 1개 이상, 소문자 1개 이상, 숫자 1개 이상, 특수문자 1개 이상이어야 합니다. : ${validatedValue}")
    private String password;

    private CustomerRequest() {
    }

    public CustomerRequest(String userName, String password) {
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
