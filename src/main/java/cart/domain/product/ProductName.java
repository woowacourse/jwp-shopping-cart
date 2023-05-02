package cart.domain.product;

import java.util.Objects;

public final class ProductName {

    private static final int MIN = 1;
    private static final int MAX = 25;

    private final String name;

    public ProductName(final String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(final String name) {
        if (name.length() < MIN || name.length() > MAX) {
            throw new IllegalArgumentException("상품 이름의 길이는 " + MIN + " ~ " + MAX + "글자여야 합니다.");
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
