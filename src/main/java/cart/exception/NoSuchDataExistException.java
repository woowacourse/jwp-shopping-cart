package cart.exception;

public class NoSuchDataExistException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "해당 데이터를 찾을 수 없습니다.";

    public NoSuchDataExistException() {
        super(DEFAULT_MESSAGE);
    }

    public NoSuchDataExistException(final String message) {
        super(message);
    }
}
