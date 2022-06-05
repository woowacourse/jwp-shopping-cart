package woowacourse.member.exception;

public class FailedEncryptException extends MemberException {

    public FailedEncryptException() {
        super("암호화에 실패하였습니다.");
    }
}

