package cart.service.product;

public class ProductPrice {
    private final int price;

    public ProductPrice(int price) {
        if (price > 1_000_000_000) {
            throw new IllegalArgumentException("상품 가격은 최대 10억입니다.");
        }
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
