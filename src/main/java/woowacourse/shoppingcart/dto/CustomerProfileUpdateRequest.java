package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.application.dto.CustomerProfileUpdateServiceRequest;
import javax.validation.constraints.NotBlank;

public class CustomerProfileUpdateRequest {

    private static final String EMPTY_NAME_MESSAGE = "이름은 공백일 수 없습니다.";

    @NotBlank(message = EMPTY_NAME_MESSAGE)
    private String name;

    private CustomerProfileUpdateRequest() {
    }

    public CustomerProfileUpdateRequest(final String name) {
        this.name = name;
    }

    public CustomerProfileUpdateServiceRequest toServiceRequest(final Long id) {
        return new CustomerProfileUpdateServiceRequest(id, name);
    }

    public String getName() {
        return name;
    }
}
