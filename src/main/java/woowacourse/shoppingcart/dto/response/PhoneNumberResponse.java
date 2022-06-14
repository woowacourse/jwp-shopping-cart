package woowacourse.shoppingcart.dto.response;

public class PhoneNumberResponse {

    private final String start;
    private final String middle;
    private final String last;

    public PhoneNumberResponse(String start, String middle, String last) {
        this.start = start;
        this.middle = middle;
        this.last = last;
    }

    public static PhoneNumberResponse from(String phoneNumber) {
        return new PhoneNumberResponse(phoneNumber.substring(0, 3), phoneNumber.substring(3, 7), phoneNumber.substring(7, 11));
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
