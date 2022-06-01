package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerRequest {

    @Pattern(regexp = "[a-z1-9_]{3,20}", message = "이름은 소문자, 숫자, 언더바(_)만 사용가능합니다😉")
    @NotBlank(message = "이름을 입력해주세요😉")
    private final String name;
    @Size(min = 8, message = "비밀번호는 8자 이상이어야합니다😉")
    @NotBlank(message = "비밀번호를 입력해주세요😉")
    private final String password;

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

