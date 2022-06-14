package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PhoneNumberFormat {

    private static final String errorMessage = "휴대폰번호 형식이 일치하지 않습니다.";

    @NotBlank
    @Pattern(regexp = "\\d{3}", message = errorMessage)
    private final String start;
    @NotBlank
    @Pattern(regexp = "\\d{4}", message = errorMessage)
    private final String middle;
    @NotBlank
    @Pattern(regexp = "\\d{4}", message = errorMessage)
    private final String last;

    @JsonCreator
    public PhoneNumberFormat(final String start, final String middle, final String last) {
        this.start = start;
        this.middle = middle;
        this.last = last;
    }

    public static PhoneNumberFormat of(final String phoneNumber) {
        return new PhoneNumberFormat(phoneNumber.substring(0, 3), phoneNumber.substring(3, 7), phoneNumber.substring(7, 11));
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
