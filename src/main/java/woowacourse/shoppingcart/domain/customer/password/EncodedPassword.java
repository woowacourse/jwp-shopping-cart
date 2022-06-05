package woowacourse.shoppingcart.domain.customer.password;

import java.util.Objects;

public class EncodedPassword {

    private final String value;

    public EncodedPassword(String value) {
        checkNull(value);
        this.value = value;
    }

    private void checkNull(String value) {
        if (value == null) {
            throw new NullPointerException("비밀번호는 필수 입력 사항입니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EncodedPassword that = (EncodedPassword)o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
