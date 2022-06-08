package woowacourse.shoppingcart.dto.response;

public class ExistsCustomerResponse {

    private Boolean isDuplicate;

    public ExistsCustomerResponse() {
    }

    public ExistsCustomerResponse(final boolean duplicated) {
        this.isDuplicate = duplicated;
    }

    public Boolean getIsDuplicate() {
        return isDuplicate;
    }
}
