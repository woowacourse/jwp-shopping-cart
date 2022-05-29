package woowacourse.member.exception;

public class WrongPasswordException extends BadRequestException {

    public WrongPasswordException() {
        super("[ERROR] 비밀번호가 틀렸습니다.");
    }
}
