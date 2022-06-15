package woowacourse.shoppingcart.dto.product;

import javax.validation.constraints.Positive;

public class ProductsRequest {

    @Positive
    private final int page;

    @Positive
    private final int limit;

    public ProductsRequest(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }
}
