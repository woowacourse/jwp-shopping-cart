package woowacourse.shoppingcart.domain.customer;

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

    public boolean isSamePassword(EncodedPassword otherPassword) {
        return value.equals(otherPassword.value);
    }

    public String getValue() {
        return value;
    }
}
