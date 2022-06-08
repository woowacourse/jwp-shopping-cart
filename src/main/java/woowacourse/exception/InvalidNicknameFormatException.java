package woowacourse.exception;

public class InvalidNicknameFormatException extends CustomException {

    public InvalidNicknameFormatException() {
        super(Error.INVALID_NICKNAME_FORMAT);
    }
}
