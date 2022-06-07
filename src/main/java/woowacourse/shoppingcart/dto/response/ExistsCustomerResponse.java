package woowacourse.shoppingcart.dto.response;

public class ExistsCustomerResponse {

    private Boolean duplicated;

    public ExistsCustomerResponse() {
    }

    public ExistsCustomerResponse(final boolean duplicated) {
        this.duplicated = duplicated;
    }

    public Boolean getDuplicated() {
        return duplicated;
    }
}
