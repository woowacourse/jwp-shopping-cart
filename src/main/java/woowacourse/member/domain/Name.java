package woowacourse.member.domain;

import woowacourse.member.exception.InvalidMemberNameException;

import java.util.Objects;

public class Name {

    private final String value;

    public Name(String value) {
        validateName(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private void validateName(String value) {
        validateNameLength(value);
        validateNameContainSpace(value);
    }

    private void validateNameLength(String value) {
        if (value.length() > 10) {
            throw new InvalidMemberNameException("이름은 10자 이하이어야 합니다.");
        }
    }

    private void validateNameContainSpace(String value) {
        if (value.contains(" ")) {
            throw new InvalidMemberNameException("이름에 공백이 포함될 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
