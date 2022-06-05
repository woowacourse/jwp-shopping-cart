package woowacourse.auth.ui.dto;

public class LoginMember {
    public static final int MINIMUM_ID_VALUE = 1;
    public static final String MESSAGE_FOR_ID_LOWER_THAN_ONE = "ID는 1보다 작을 수 없습니다";

    private final long id;

    private LoginMember(long id) {
        validateId(id);
        this.id = id;
    }

    private void validateId(long id) {
        if (id < MINIMUM_ID_VALUE) {
            throw new IllegalArgumentException(MESSAGE_FOR_ID_LOWER_THAN_ONE);
        }
    }

    public static LoginMember from(long id) {
        return new LoginMember(id);
    }

    public long getId() {
        return id;
    }
}
