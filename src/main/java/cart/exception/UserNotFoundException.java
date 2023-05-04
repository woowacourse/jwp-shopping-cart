package cart.exception;

public final class UserNotFoundException extends GlobalException {

    private static final String message = "해당 유저를 찾을 수 없습니다. 입력된 email : %s";

    public UserNotFoundException(final String userEmail) {
        super(String.format(message, userEmail));
    }
}
