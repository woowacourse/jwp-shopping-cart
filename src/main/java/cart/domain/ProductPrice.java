package cart.domain;

public class ProductPrice {
    private final Long price;

    public ProductPrice(final Integer price) {
        this.price = Long.valueOf(price);
    }

    public ProductPrice(final Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }
}
