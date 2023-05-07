package cart.domain.member;

import java.util.Objects;

public class MemberName {

    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 50;

    private final String name;

    public MemberName(String name) {
        this.name = name;
        validate(this.name);
    }

    //todo : 에러메시지 50 숫자 포맷
    private void validate(String name) {
        if(name != null && (name.length() <MIN_LENGTH ||name.length() > MAX_LENGTH)) {
            throw new IllegalArgumentException("회원 이름의 길이는 1자 이상 50자 이하여야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MemberName that = (MemberName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
