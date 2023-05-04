package cart.dto.common;

import cart.exception.InvalidIdException;
import java.util.Objects;

public class Id {
    private final Long id;

    private Id(Long id) {
        validatePositive(id);
        this.id = id;
    }

    private void validatePositive(Long id) {
        if (id <= 0) {
            throw new InvalidIdException("ID는 양수만 가능합니다.");
        }
    }

    public static Id from(String id) {
        validateNull(id);
        return new Id(parseId(id));
    }

    private static void validateNull(String id) {
        if (Objects.isNull(id) || id.isBlank()) {
            throw new InvalidIdException("ID는 빈 값이 될 수 없습니다.");
        }
    }

    private static Long parseId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InvalidIdException("ID는 숫자만 가능합니다.");
        }
    }

    public Long getId() {
        return id;
    }
}
