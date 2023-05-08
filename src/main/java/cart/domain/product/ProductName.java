package cart.domain.product;

import cart.exception.BadRequestException;

import java.util.Objects;

public final class ProductName {

    private static final int MIN = 1;
    private static final int MAX = 25;
    private static final String ERROR_MESSAGE = "상품 이름의 길이는 " + MIN + " ~ " + MAX + "글자여야 합니다.";

    private final String name;

    public ProductName(final String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(final String name) {
        if (name.length() < MIN || name.length() > MAX) {
            throw new BadRequestException(name, ERROR_MESSAGE);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductName other = (ProductName) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
