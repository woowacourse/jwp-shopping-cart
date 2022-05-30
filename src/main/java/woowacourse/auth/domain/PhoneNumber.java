package woowacourse.auth.domain;

import java.util.regex.Pattern;

public class PhoneNumber {

    private static final Pattern FORMAT = Pattern.compile("^[0-9]+$");

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
        if (!FORMAT.matcher(start + middle + last).matches()) {
            throw new IllegalArgumentException(
                    String.format("휴대폰 번호는 숫자만 가능합니다. %s-%s-%s", start, middle, last));
        }
    }
}
