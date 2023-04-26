package cart.exception;

import cart.error.exception.CartException;
import cart.error.exception.ErrorCode;

public class CantSellNegativeQuantity extends CartException {

    public static final CartException EXCEPTION = new CantSellNegativeQuantity();

    public CantSellNegativeQuantity() {
        super(ErrorCode.CANT_SELL_NEGATIVE_QUANTITY);
    }

}
