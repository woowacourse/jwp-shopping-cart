package woowacourse.shoppingcart.dto.product;

import javax.validation.constraints.NotNull;

public class PageProductsRequest {

    @NotNull
    private Integer page;

    @NotNull
    private Integer size = 10;

    public PageProductsRequest() {
    }

    public PageProductsRequest(final int page, final int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
