package woowacourse.shoppingcart.exception;

public class NotInMemberCartItemException extends CartException {

    public NotInMemberCartItemException() {
        super("장바구니 아이템이 없습니다.");
    }
}
