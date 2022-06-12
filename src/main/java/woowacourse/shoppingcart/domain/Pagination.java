package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidProductException;

public class Pagination {

    private final int size;
    private final int page;

    public Pagination(int size, int page) {
        this.size = size;
        this.page = page;
    }

    private void validatePositiveSize(int size) {
        if (size <= 0) {
            throw new InvalidProductException("[ERROR] 페이지당 갯수는 자연수여야 합니다.");
        }
    }

    private void validatePositivePage(int page) {
        if (page <= 0) {
            throw new InvalidProductException("[ERROR] 페이지는 자연수여야 합니다.");
        }
    }

    public boolean isOverMaxPage(Long totalCount, int page) {
        long totalPage = (long) Math.ceil(totalCount / (double) size);
        return page > totalPage;
    }
}
