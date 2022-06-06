package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.application.dto.PageServiceRequest;

public class PageRequest {

    private Integer page;
    private Integer size;

    private PageRequest() {
    }

    public PageRequest(final Integer page, final Integer size) {
        this.page = page;
        this.size = size;
    }

    public PageServiceRequest toServiceDto() {
        return new PageServiceRequest(page, size);
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }
}
