package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.Pattern;

public class PhoneNumberFormat {

    private static final String errorMessage = "휴대폰번호 형식이 일치하지 않습니다.";
    private static final int PHONE_NUMBER_START_BEGIN_INDEX = 0;
    private static final int PHONE_NUMBER_START_LAST_INDEX = 3;
    private static final int PHONE_NUMBER_MIDDLE_LAST_INDEX = 7;
    private static final int PHONE_NUMBER_END_LAST_INDEX = 11;

    @Pattern(regexp = "\\d{3}", message = errorMessage)
    private final String start;
    @Pattern(regexp = "\\d{4}", message = errorMessage)
    private final String middle;
    @Pattern(regexp = "\\d{4}", message = errorMessage)
    private final String last;

    @JsonCreator
    public PhoneNumberFormat(String start, String middle, String last) {
        this.start = start;
        this.middle = middle;
        this.last = last;
    }

    public static PhoneNumberFormat of(String phoneNumber) {
        return new PhoneNumberFormat(
                phoneNumber.substring(PHONE_NUMBER_START_BEGIN_INDEX, PHONE_NUMBER_START_LAST_INDEX),
                phoneNumber.substring(PHONE_NUMBER_START_LAST_INDEX, PHONE_NUMBER_MIDDLE_LAST_INDEX),
                phoneNumber.substring(PHONE_NUMBER_MIDDLE_LAST_INDEX, PHONE_NUMBER_END_LAST_INDEX));
    }

    public String getStart() {
        return start;
    }

    public String getMiddle() {
        return middle;
    }

    public String getLast() {
        return last;
    }

    public String appendNumbers() {
        return this.start + this.middle + this.last;
    }
}
