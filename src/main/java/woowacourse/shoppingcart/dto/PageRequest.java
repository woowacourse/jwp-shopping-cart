package woowacourse.shoppingcart.dto;

public class PageRequest {

    private Integer page;
    private Integer size;

    private PageRequest() {
    }

    public PageRequest(final Integer page, final Integer size) {
        this.page = page;
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }
}
