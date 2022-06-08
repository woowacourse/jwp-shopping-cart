package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

public class PhoneNumber {

    private static final int FULL_PHONE_NUMBER_LENGTH = 11;
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d*$");
    private static final String errorMessage = "휴대폰번호 형식이 일치하지 않습니다.";

    private final String value;

    private void validatePhoneNumberLength(String phoneNumber) {
        if (phoneNumber.length() != FULL_PHONE_NUMBER_LENGTH) {
            throw new IllegalArgumentException("휴대폰번호 형식이 일치하지 않습니다.");
        }
    }

    private void validateDigit(final String phoneNumber) {
        if (!DIGIT_PATTERN.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("휴대폰 번호는 숫자만 가능합니다.");
        }
    }

    public PhoneNumber(String phoneNumber) {
        validatePhoneNumberLength(phoneNumber);
        validateDigit(phoneNumber);
        this.value = phoneNumber;
    }

    public String getValue() {
        return value;
    }
}
