package woowacourse.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Pattern;

public class PhoneNumber {

    private static final String errorMessage = "휴대폰번호 형식이 일치하지 않습니다.";

    @Pattern(regexp = "\\d{3}", message = errorMessage)
    private final String start;
    @Pattern(regexp = "\\d{4}", message = errorMessage)
    private final String middle;
    @Pattern(regexp = "\\d{4}", message = errorMessage)
    private final String end;

    @JsonCreator
    public PhoneNumber(final String start, final String middle, final String end) {
        this.start = start;
        this.middle = middle;
        this.end = end;
    }

    public static PhoneNumber of(final String phoneNumber) {
        return new PhoneNumber(phoneNumber.substring(0, 3), phoneNumber.substring(3, 7), phoneNumber.substring(7, 11));
    }

    public String getStart() {
        return start;
    }

    public String getMiddle() {
        return middle;
    }

    public String getEnd() {
        return end;
    }

    public String appendNumbers() {
        return this.start + this.middle + this.end;
    }
}
