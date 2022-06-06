package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Positive;

public class ProductsPerPageRequest {

    @Positive
    private Integer size;

    @Positive
    private Integer page;

    public ProductsPerPageRequest() {
    }

    public ProductsPerPageRequest(Integer size, Integer page) {
        this.size = size;
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getPage() {
        return page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
