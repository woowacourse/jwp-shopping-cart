package cart.domain.product;

import java.util.Objects;

public class ProductName {

    private static final int MAX_LENGTH = 50;

    private final String name;

    public ProductName(String name) {
        this.name = name;
        validate(this.name);
    }

    private void validate(String name) {
        if(name.isBlank()) {
            throw new IllegalArgumentException("상품 이름은 공백일 수 없습니다.");
        }

        if(name.length() > MAX_LENGTH) {
            //todo : 에러메시지 50 숫자 포맷
            throw new IllegalArgumentException("상품 이름의 길이는 50자 이하여야 합니다.");
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductName that = (ProductName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
