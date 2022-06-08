package woowacourse.shoppingcart.exception.invalid;

public class InvalidIdException extends InvalidException {

    public InvalidIdException() {
        super("아이디는 1 이상의 정수여야합니다😉");
    }
}
