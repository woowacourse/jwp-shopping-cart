package woowacourse.exception.notfound;

import woowacourse.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {
    private static final String DEFAULT_MESSAGE = "요청하신 제품이 존재하지 않습니다";

    public ProductNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
