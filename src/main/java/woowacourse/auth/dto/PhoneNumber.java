package woowacourse.auth.dto;

public class PhoneNumber {

    private final String start;
    private final String middle;
    private final String end;

    public PhoneNumber(String start, String middle, String end) {
        this.start = start;
        this.middle = middle;
        this.end = end;
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
}
