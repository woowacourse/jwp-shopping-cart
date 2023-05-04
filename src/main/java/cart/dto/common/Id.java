package cart.dto.common;

import java.util.Objects;

public class Id {
    private final Long id;

    private Id(Long id) {
        validatePositive(id);
        this.id = id;
    }

    private void validatePositive(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID 값은 양수만 가능합니다.");
        }
    }

    public static Id from(String id) {
        validateNull(id);
        return new Id(Long.parseLong(id));
    }

    private static void validateNull(String id) {
        if (Objects.isNull(id) || id.isBlank()) {
            throw new IllegalArgumentException("ID는 빈 값이 될 수 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }
}
