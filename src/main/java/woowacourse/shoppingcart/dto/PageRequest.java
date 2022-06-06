package woowacourse.shoppingcart.dto;

public class PageRequest {

    private Long size;
    private Long page;

    private PageRequest() {
    }

    public PageRequest(Long size, Long page) {
        this.size = size;
        this.page = page - 1;
    }

    public Long getSize() {
        return size;
    }

    public Long getPage() {
        return page;
    }
}
