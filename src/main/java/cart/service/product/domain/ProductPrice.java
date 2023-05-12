package cart.service.product.domain;

public class ProductPrice {
    private static final int MAX_PRICE = 1_000_000_000;
    private final int price;

    public ProductPrice(int price) {
        validate(price);
        this.price = price;
    }

    private void validate(int price) {
        if (price > MAX_PRICE) {
            throw new IllegalArgumentException("상품 가격은 최대 10억입니다.");
        }
    }

    public int getPrice() {
        return price;
    }
}