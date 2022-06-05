package woowacourse.shoppingcart.exception;

public class DeleteException extends IllegalArgumentException {
    public DeleteException() {
        super("삭제에 실패했습니다.");
    }
}
