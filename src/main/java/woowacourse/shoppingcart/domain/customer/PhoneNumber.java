package woowacourse.shoppingcart.domain.customer;

public class PhoneNumber {

    private static final int LIMIT_LENGTH = 11;

    private final String phoneNumber;

    public PhoneNumber(final String phoneNumber) {
        validateLength(phoneNumber);
        this.phoneNumber = phoneNumber;
    }

    private void validateLength(final String phoneNumber) {
        if (phoneNumber.length() != LIMIT_LENGTH) {
            throw new IllegalArgumentException(String.format("핸드폰 번호는 %d자여야합니다.", LIMIT_LENGTH));
        }
    }
}
