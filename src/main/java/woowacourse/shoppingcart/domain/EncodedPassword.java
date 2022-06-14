package woowacourse.shoppingcart.domain;

import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodedPassword {

    private final String value;

    public EncodedPassword(final String value) {
        validateBlank(value);
        this.value = value;
    }

    private void validateBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("비밀번호는 비어있을 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }

    public boolean isNotMatch(final PasswordEncoder passwordEncoder, final String password) {
        return !passwordEncoder.matches(password, this.value);
    }
}
