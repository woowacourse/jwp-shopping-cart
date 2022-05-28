package woowacourse.auth.domain;

import java.util.regex.Pattern;

public class Email {

    private static final Pattern PATTERN = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]"
            + "+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

    private final String value;

    public Email(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
