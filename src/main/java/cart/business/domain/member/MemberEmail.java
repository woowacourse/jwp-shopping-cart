package cart.business.domain.member;

import java.util.regex.Pattern;

public class MemberEmail {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(\\S+)$");

    private final String email;

    public MemberEmail(String email) {
        validate(email);
        this.email = email;
    }

    private void validate(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일 형식이 맞지 않습니다.");
        }
    }

    public String getValue() {
        return email;
    }
}
