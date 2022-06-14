package woowacourse.shoppingcart.domain;

public class Account {

    private static final int MINIMUM_LENGTH = 4;
    private static final int MAXIMUM_LENGTH = 15;
    private static final String MATCH_PATTERN = "^[a-z\\d]*$";

    private final String value;

    public Account(final String value) {
        validateBlank(value);
        validateLength(value);
        validatePattern(value);
        this.value = value;
    }

    private void validateBlank(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("아이디는 비어있을 수 없습니다.");
        }
    }

    private void validateLength(final String value) {
        if (value.length() < MINIMUM_LENGTH || value.length() > MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("아이디 길이는 %d~%d자를 만족해야 합니다.", MINIMUM_LENGTH, MAXIMUM_LENGTH)
            );
        }
    }

    private void validatePattern(final String value) {
        if (!value.matches(MATCH_PATTERN)) {
            throw new IllegalArgumentException("아이디는 영어 혹은 숫자의 조합으로 이루어져야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
