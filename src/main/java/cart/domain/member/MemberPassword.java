package cart.domain.member;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class MemberPassword {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 50;

    @NotBlank
    @Length(min = MIN_LENGTH, max = MAX_LENGTH, message = MIN_LENGTH + "-" + MAX_LENGTH + "자 사이의 비밀번호를 입력해 주세요.")
    private final String value;

    public MemberPassword(final String password) {
        this.value = password;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MemberPassword memberPassword = (MemberPassword) o;
        return Objects.equals(value, memberPassword.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
