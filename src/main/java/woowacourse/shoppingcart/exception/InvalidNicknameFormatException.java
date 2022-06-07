package woowacourse.shoppingcart.exception;

public class InvalidNicknameFormatException extends IllegalArgumentException {

    public InvalidNicknameFormatException() {
        super("닉네임 형식에 맞지 않습니다.");
    }
}
