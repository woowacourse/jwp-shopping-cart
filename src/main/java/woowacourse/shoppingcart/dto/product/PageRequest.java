package woowacourse.shoppingcart.dto.product;

import javax.validation.constraints.Min;

public class PageRequest {

    @Min(value = 1, message = "페이지는 1 이상이어야 합니다.")
    private int page;
    @Min(value = 1, message = "한 페이지의 제품 개수는 1 이상이어야 합니다.")
    private int limit;

    public PageRequest() {
    }

    public PageRequest(int page, int limit) {
        this.page = page;
        this.limit = limit;
    }

    public int calculateOffset() {
        return (page - 1) * limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
