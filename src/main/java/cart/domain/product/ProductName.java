package cart.domain.product;

public class ProductName {
    private static final int MAX_LENGTH = 20;
    private final String name;

    public ProductName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException();
        }
    }
}
