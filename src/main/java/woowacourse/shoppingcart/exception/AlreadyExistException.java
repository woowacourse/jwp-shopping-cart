package woowacourse.shoppingcart.exception;

public class AlreadyExistException extends RuntimeException{

    public AlreadyExistException() {
        super("이미 존재하는 회원입니다.");
    }

}
