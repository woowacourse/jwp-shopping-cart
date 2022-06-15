package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;

public class CustomerUpdateProfileRequest {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    public CustomerUpdateProfileRequest() {
    }

    public CustomerUpdateProfileRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
