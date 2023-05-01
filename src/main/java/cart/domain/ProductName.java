package cart.domain;

public class ProductName {

    private final int MAX_LENGTH = 50;

    private final String name;

    public ProductName(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("상품명은 50글자 이하여야합니다.");
        }
    }

    public String getName() {
        return name;
    }
}
