package woowacourse.auth.domain;

public class PhoneNumber {

    private String start;
    private String middle;
    private String last;

    public PhoneNumber(String start, String middle, String last) {
        validate(start, middle, last);
        this.start = start;
        this.middle = middle;
        this.last = last;
    }

    private void validate(String start, String middle, String last) {
        if (start.length() != 3 || middle.length() != 4 || last.length() != 4) {
            throw new IllegalArgumentException(
                    String.format("휴대폰 번호양식이 불일치 합니다. %s-%s-%s", start, middle, last));
        }
    }
}
