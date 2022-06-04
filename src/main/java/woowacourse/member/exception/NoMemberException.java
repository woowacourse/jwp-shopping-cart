package woowacourse.member.exception;

public class NoMemberException extends BadRequestException {

    public NoMemberException() {
        super("[ERROR] 멤버가 존재하지 않습니다.");
    }
}
