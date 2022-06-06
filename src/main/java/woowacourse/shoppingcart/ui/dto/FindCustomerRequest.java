package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.NotEmpty;

public class FindCustomerRequest {

    @NotEmpty(message = "이름은 비어있을 수 없습니다.")
    private final Long id;

    public FindCustomerRequest(Long id) {
        this.id = id;
    }

    public FindCustomerRequest(String idString) {
        this(Long.parseLong(idString));
    }

    public Long getId() {
        return id;
    }
}
