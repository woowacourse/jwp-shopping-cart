package cart.domain.member;

public class Password {

    private final String value;

    public Password(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 빈칸이 될 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}

