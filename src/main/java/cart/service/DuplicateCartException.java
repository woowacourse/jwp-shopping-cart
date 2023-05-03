package cart.service;

public class DuplicateCartException extends RuntimeException {
    private static final String MESSAGE = "이미 추가된 상품입니다.";

    public DuplicateCartException() {
        super(MESSAGE);
    }
}
