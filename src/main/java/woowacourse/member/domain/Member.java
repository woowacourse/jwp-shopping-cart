package woowacourse.member.domain;

import woowacourse.member.exception.InvalidMemberEmailException;
import woowacourse.member.exception.InvalidMemberNameException;

public class Member {

    private final String email;
    private final String name;
    private final Password password;

    public Member(String email, String name, String password) {
        validateEmailForm(email);
        validateName(name);
        this.email = email;
        this.name = name;
        this.password = new Password(password);
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

    private void validateEmailForm(String email) {
        if (!email.contains("@")) {
            throw new InvalidMemberEmailException("올바르지 못한 이메일 형식입니다.");
        }
    }

    private void validateName(String name) {
        validateNameLength(name);
        validateNameContainSpace(name);
    }

    private void validateNameLength(String name) {
        if (name.length() > 10) {
            throw new InvalidMemberNameException("이름은 10자 이하이어야 합니다.");
        }
    }

    private void validateNameContainSpace(String name) {
        if (name.contains(" ")) {
            throw new InvalidMemberNameException("이름에 공백이 포함될 수 없습니다.");
        }
    }
}
