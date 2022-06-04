package woowacourse.member.domain;

import woowacourse.member.exception.InvalidMemberEmailException;
import woowacourse.member.exception.InvalidMemberNameException;

import java.util.regex.Pattern;

public class Member {

    private static final Pattern EMAIL_REGEX_PATTERN = Pattern.compile("^(.+)@(.+)$");
    private static final int MAXIMUM_NAME_LENGTH = 10;
    private static final String SPACE_CHARACTER = " ";

    private final Long id;
    private final String email;
    private final String name;
    private final Password password;

    private Member(Long id, String email, String name, Password password) {
        validateEmailForm(email);
        validateName(name);
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public static Member withEncrypt(String email, String name, String password) {
        return new Member(0L, email, name, Password.withEncrypt(password));
    }

    public static Member withoutEncrypt(Long id, String email, String name, String password) {
        return new Member(id, email, name, Password.withoutEncrypt(password));
    }

    private void validateEmailForm(String email) {
        if (!EMAIL_REGEX_PATTERN.matcher(email).find()) {
            throw new InvalidMemberEmailException("올바르지 못한 이메일 형식입니다.");
        }
    }

    private void validateName(String name) {
        validateNameLength(name);
        validateNameContainSpace(name);
    }

    private void validateNameLength(String name) {
        if (name.length() > MAXIMUM_NAME_LENGTH) {
            throw new InvalidMemberNameException("이름은 10자 이하이어야 합니다.");
        }
    }

    private void validateNameContainSpace(String name) {
        if (name.contains(SPACE_CHARACTER)) {
            throw new InvalidMemberNameException("이름에 공백이 포함될 수 없습니다.");
        }
    }

    public boolean isSamePassword(Password comparison) {
        return password.equals(comparison);
    }

    public boolean isSameName(String comparison) {
        return name.equals(comparison);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password.getValue();
    }
}
