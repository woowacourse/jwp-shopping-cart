package cart.domain.product;

public class Price {
    private static final int MIN_PRICE = 0;
    private static final int MAX_PRICE = 10_000_000;
    private final int price;

    public Price(int price) {
        validateSize(price);
        this.price = price;
    }

    private void validateSize(int price) {
        if (price <= MIN_PRICE) {
            throw new IllegalArgumentException("가격은 " + MIN_PRICE + "원 이하 일 수 없습니다.");
        }
        if (price >= MAX_PRICE) {
            throw new IllegalArgumentException("가격은 " + MAX_PRICE + "원 이상 일 수 없습니다.");
        }
    }

    public int getPrice() {
        return price;
    }
}
