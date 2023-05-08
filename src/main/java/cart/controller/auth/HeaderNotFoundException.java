package cart.controller.auth;

public class HeaderNotFoundException extends IllegalArgumentException {

    private static final String MESSAGE = "인증 헤더가 존재하지 않습니다.";

    public HeaderNotFoundException() {
        super(MESSAGE);
    }
}
