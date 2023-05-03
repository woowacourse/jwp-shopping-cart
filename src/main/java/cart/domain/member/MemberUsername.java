package cart.domain.member;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class MemberUsername {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 100;

    @NotBlank
    @Length(min = MIN_LENGTH, max = MAX_LENGTH, message = MIN_LENGTH + "-" + MAX_LENGTH + "자 사이의 사용자명을 입력해 주세요.")
    private final String value;

    public MemberUsername(final String username) {
        this.value = username;
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
        final MemberUsername memberUsername = (MemberUsername) o;
        return Objects.equals(value, memberUsername.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
