package woowacourse.shoppingcart.dto;

public class ExistCustomerResponse {
    private Boolean isDuplicate;

    public ExistCustomerResponse() {
    }

    public ExistCustomerResponse(Boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    public Boolean getDuplicated() {
        return isDuplicate;
    }
}
