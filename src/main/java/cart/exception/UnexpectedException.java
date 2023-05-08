package cart.exception;

public final class UnexpectedException extends GlobalException {

    private static final String message = "서버에 예상치 못한 문제가 발생했습니다. 잠시후 다시 시도해주세요";

    public UnexpectedException() {
        super(message);
    }
}
