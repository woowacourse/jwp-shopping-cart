package cart.domain.product;

public class ProductName {
    private static final int MAX_LENGTH = 20;
    private final String name;

    private ProductName(String name) {
        validate(name);
        this.name = name;
    }

    public static ProductName from(String name) {
        return new ProductName(name);
    }

    private void validate(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException();
        }

        if (name.length() > MAX_LENGTH) {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }
}
