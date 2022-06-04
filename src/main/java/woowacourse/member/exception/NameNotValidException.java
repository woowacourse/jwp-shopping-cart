package woowacourse.member.exception;

public class NameNotValidException extends BadRequestException {

    public NameNotValidException() {
        super("[ERROR] 이름이 올바르지 않습니다.");
    }
}
