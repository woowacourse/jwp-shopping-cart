package cart.entity.vo;

public class Email {

    private final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private final String value;

    public Email(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        if (!value.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("email이 형식에 맞지 않습니다. 입력된 값 : " + value);
        }
    }

    public String getValue() {
        return value;
    }
}
