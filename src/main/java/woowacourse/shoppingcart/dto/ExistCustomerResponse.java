package woowacourse.shoppingcart.dto;

public class ExistCustomerResponse {
    private final Boolean isDuplicate;

    public ExistCustomerResponse(Boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    public Boolean getIsDuplicate() {
        return isDuplicate;
    }
}
