package woowacourse.member.exception;

public class PasswordChangeException extends BadRequestException {

    public PasswordChangeException() {
        super("[ERROR] 비밀번호를 변경할 수 없습니다.");
    }
}
