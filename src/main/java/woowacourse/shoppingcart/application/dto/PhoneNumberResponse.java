package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.customer.vo.PhoneNumber;

public class PhoneNumberResponse {

    private final String start;
    private final String middle;
    private final String last;

    public PhoneNumberResponse(String start, String middle, String last) {
        this.start = start;
        this.middle = middle;
        this.last = last;
    }

    public static PhoneNumberResponse from(PhoneNumber phoneNumber) {
        return new PhoneNumberResponse(
                phoneNumber.getStart(),
                phoneNumber.getMiddle(),
                phoneNumber.getLast()
        );
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
}
