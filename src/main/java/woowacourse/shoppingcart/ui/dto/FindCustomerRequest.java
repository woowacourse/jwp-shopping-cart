package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.NotEmpty;

public class FindCustomerRequest {

    @NotEmpty(message = "이름은 비어있을 수 없습니다.")
    private final String name;

    public FindCustomerRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
