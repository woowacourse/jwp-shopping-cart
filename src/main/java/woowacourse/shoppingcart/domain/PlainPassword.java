package woowacourse.shoppingcart.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PlainPassword {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;
    private static final String PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)).*";

    private final String value;

    public PlainPassword(String value) {
        validatePassword(value);
        this.value = value;
    }

    private void validatePassword(final String value) {
        validateBlank(value);
        validateLength(value);
        validatePattern(value);
    }

    private void validateBlank(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 비어있을 수 없습니다.");
        }
    }

    private void validateLength(final String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("비밀번호 길이는 %d~%d자를 만족해야 합니다.", MIN_LENGTH, MAX_LENGTH)
            );
        }
    }

    private void validatePattern(final String value) {
        if (!value.matches(PATTERN)) {
            throw new IllegalArgumentException("비밀번호는 대소문자, 숫자, 특수문자가 반드시 1개 이상 포함되어야 합니다.");
        }
    }

    public String encode(final PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(value);
    }
}
