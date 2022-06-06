package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.Min;

public class FindCustomerRequest {

    @Min(0)
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
