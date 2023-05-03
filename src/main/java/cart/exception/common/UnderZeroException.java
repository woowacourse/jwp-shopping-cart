package cart.exception.common;

public class UnderZeroException extends CommonException{

    private static final String UNDER_ZERO_ERROR_MESSAGE = "0보다 작은 값이 될 수 없습니다.";

    public UnderZeroException() {
        super(UNDER_ZERO_ERROR_MESSAGE);
    }
}
