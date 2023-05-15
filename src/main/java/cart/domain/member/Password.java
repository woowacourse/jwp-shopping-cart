package cart.domain.member;

public class Password {

    private static final String NULL_ERROR_MESSAGE = "비밀번호를 입력해주세요.";
    private final String value;

    public Password(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException(NULL_ERROR_MESSAGE);
        }
    }

    public String getValue() {
        return value;
    }
}
