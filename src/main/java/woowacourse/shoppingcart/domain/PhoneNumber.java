package woowacourse.shoppingcart.domain;

public class PhoneNumber {

    private static final int PHONE_NUMBER_LENGTH = 11;

    private final String value;

    public PhoneNumber(final String value) {
        validatePhoneNumber(value);
        this.value = value;
    }

    private void validatePhoneNumber(final String phoneNumber) {
        validateBlank(phoneNumber);
        validateLength(phoneNumber);
    }

    private void validateBlank(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("핸드폰 번호는 비어있을 수 없습니다.");
        }
    }

    private void validateLength(String phoneNumber) {
        if (phoneNumber.length() != PHONE_NUMBER_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("핸드폰 번호 길이는 %d자 이어야 합니다.", PHONE_NUMBER_LENGTH)
            );
        }
    }

    public String getValue() {
        return value;
    }
}
