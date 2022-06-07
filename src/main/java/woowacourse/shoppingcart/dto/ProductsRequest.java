package woowacourse.shoppingcart.dto;

public class ProductsRequest {

    private final int page;
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
