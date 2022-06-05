package woowacourse.shoppingcart.application.exception;

public final class CannotDeleteException extends BusinessException {

    public CannotDeleteException() {
        this("해당 데이터 삭제에 실패했습니다.");
    }

    public CannotDeleteException(String message) {
        super(message);
    }
}
