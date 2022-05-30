package woowacourse.member.exception;

public class MemberCreateException extends BadRequestException{

    public MemberCreateException(String cause) {
        super("[ERROR] 멤버를 생성할 수 없습니다. cause = " + cause);
    }
}
