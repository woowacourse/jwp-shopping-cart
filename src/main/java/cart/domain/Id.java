package cart.domain;

import java.util.Objects;

public class Id {

    public static final Id EMPTY_ID = new Id(null);
    private final Long id;

    public Id(Long id) {
        this.id = id;
        validate(this.id);
    }

    private void validate(Long productId) {
        if(id !=null && productId <= 0) {
            throw new IllegalArgumentException("id는 0이 아닌 양수여야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Id id1 = (Id) o;
        return Objects.equals(id, id1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
