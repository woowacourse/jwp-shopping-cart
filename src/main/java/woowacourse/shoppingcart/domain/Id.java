package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidInformationException;

import java.util.Objects;

public class Id {
    private static final String PREFIX = "[ERROR] ";

    private static final String NOT_POSITIVE = " ID는 자연수여야 합니다.";

    private Long id;

    private Id(Long id) {
        this.id = id;
    }

    public static Id from(Long id, String fieldName) {
        validateId(id, fieldName);
        return new Id(id);
    }

    private static void validateId(Long id, String fieldName) {
        if (id == null || id <= 0) {
            throw new InvalidInformationException(PREFIX + fieldName + NOT_POSITIVE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id1 = (Id) o;
        return Objects.equals(id, id1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }
}
