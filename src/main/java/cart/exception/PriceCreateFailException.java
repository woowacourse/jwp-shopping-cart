package cart.exception;

public class PriceCreateFailException extends RuntimeException {

    public PriceCreateFailException() {
        super("최소 가격은 0원 이상입니다.");
    }
}
