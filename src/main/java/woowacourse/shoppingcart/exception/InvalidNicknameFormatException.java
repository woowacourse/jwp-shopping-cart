package woowacourse.shoppingcart.exception;

public class InvalidNicknameFormatException extends IllegalArgumentException {

    public InvalidNicknameFormatException() {
        super("닉네임의 길이는 2~10자 사이여야 합니다.");
    }
}
