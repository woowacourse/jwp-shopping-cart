package cart.business.domain.member;

import java.util.Objects;

public class MemberId {

    private final Integer id;

    public MemberId(Integer id) {
        this.id = id;
    }

    public Integer getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberId memberId = (MemberId) o;
        return id.equals(memberId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
