package woowacourse.customer.domain;

import java.util.Objects;

import woowacourse.auth.exception.InvalidLoginException;
import woowacourse.customer.support.passwordencoder.PasswordEncoder;

public class EncodedPassword {

    private final String value;

    public EncodedPassword(final String value) {
        this.value = value;
    }

    public void matches(final PasswordEncoder passwordEncoder, final String raw) {
        if (!passwordEncoder.matches(raw, value)) {
            throw new InvalidLoginException("로그인 정보가 시스템에 있는 계정과 일치하지 않습니다.");
        }
    }

    public EncodedPassword update(final String value) {
        return new EncodedPassword(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        final EncodedPassword that = (EncodedPassword)o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
