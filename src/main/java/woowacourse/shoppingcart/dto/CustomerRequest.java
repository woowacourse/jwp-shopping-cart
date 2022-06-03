package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class CustomerRequest {

    @Pattern(regexp = "[a-z1-9_]{5,20}", message = "이름은 5~20자에 소문자, 숫자, 언더바(_)만 사용가능합니다😉")
    @NotBlank(message = "이름을 입력해주세요😉")
    private String name;
    @Pattern(regexp = "[a-zA-Z1-9!@#$%^&*_-]{8,10}", message = "비밀번호는 8~10자에 소문자, 대문자, 특수문자만 사용가능합니다😉")
    @NotBlank(message = "비밀번호를 입력해주세요😉")
    private String password;

    public CustomerRequest() {
    }

    public CustomerRequest(final String name, final String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

