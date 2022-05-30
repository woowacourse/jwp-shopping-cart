package woowacourse.shoppingcart.exception;

public class CannotDeleteException extends RuntimeException {

    public CannotDeleteException() {
        this("해당 데이터 삭제에 실패했습니다.");
    }

    public CannotDeleteException(String message) {
        super(message);
    }
}
