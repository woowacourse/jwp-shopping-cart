package cart.domain.member;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberEmail {

    private static final String regex = "^([0-9a-zA-Z]+)@[0-9a-zA-Z]+\\.[0-9a-zA-Z]+$";
    private static final Pattern p = Pattern.compile(regex);
    private final String email;

    public MemberEmail(String email) {
        this.email = email;
        validate(this.email);
    }

    private void validate(String email) {
        if (email.isBlank()) {
            throw new IllegalArgumentException("회원 이메일은 공백일 수 없습니다.");
        }

        Matcher m = p.matcher(email);
        if (!m.matches()) {
            throw new IllegalArgumentException("회원 이메일 형식을 지키지 않았습니다.");
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberEmail that = (MemberEmail) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
