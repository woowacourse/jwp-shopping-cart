package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;

public class EncodedPassword {

    public static final int ENCODED_LENGTH = 64;

    private final String value;

    public EncodedPassword(String value) {
        validateLength(value);
        this.value = value;
    }

    private void validateLength(String value) {
        if (value.length() != ENCODED_LENGTH) {
            throw new IllegalArgumentException("암호화된 패스워드로만 생성할 수 있습니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EncodedPassword that = (EncodedPassword) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
