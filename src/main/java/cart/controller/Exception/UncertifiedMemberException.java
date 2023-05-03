package cart.controller.Exception;

public class UncertifiedMemberException extends RuntimeException {
    public UncertifiedMemberException() {
        super("[ERROR] 등록되지 않은 사용자입니다.");
    }
}
