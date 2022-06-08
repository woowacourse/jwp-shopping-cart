package woowacourse.shoppingcart.domain;

import woowacourse.common.exception.OrderException;
import woowacourse.common.exception.ProductException;
import woowacourse.common.exception.dto.ErrorResponse;

public class StockQuantity {
    private int value;

    public StockQuantity(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new ProductException("수량이 올바르지 않습니다.", ErrorResponse.INVALID_QUANTITY);
        }
    }

    public void reduce(int value) {
        validateReducible(value);
        this.value -= value;
    }

    private void validateReducible(int value) {
        if (this.value - value < 0) {
            throw new OrderException("더 많은 양을 구매할 수 없습니다.", ErrorResponse.OUT_OF_STOCK);
        }
    }

    public int getValue() {
        return value;
    }
}
