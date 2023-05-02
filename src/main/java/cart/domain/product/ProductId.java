package cart.domain.product;

public class ProductId {
    private final Long value;

    public ProductId(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
