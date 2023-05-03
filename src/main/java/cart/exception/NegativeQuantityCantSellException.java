package cart.exception;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;

public class NegativeQuantityCantSellException extends CartException {

    public static final CartException EXCEPTION = new NegativeQuantityCantSellException();

    public NegativeQuantityCantSellException() {
        super(new ErrorCode(400, "PRODUCT-400-1", "0보다 낮은 가격으로 설정할 수 없습니다."));
    }

}
