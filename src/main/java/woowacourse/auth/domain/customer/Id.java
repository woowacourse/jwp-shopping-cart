package woowacourse.auth.domain.customer;

import java.util.Objects;

public class Id {
    private static final Id EMPTY = new Id(null);

    private final Integer value;

    public Id(Integer value) {
        this.value = value;
    }

    public static Id empty() {
        return EMPTY;
    }

    public Integer getValue() {
        return Objects.requireNonNull(value, "해당 객체는 id를 가지고 있지 않습니다.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Id id = (Id) o;
        return Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Id{" +
                "value=" + value +
                '}';
    }
}
