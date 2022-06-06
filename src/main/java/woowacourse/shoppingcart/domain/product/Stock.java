package woowacourse.shoppingcart.domain.product;

public class Stock {

    private final int stock;

    public Stock(int stock) {
        validateNotNegative(stock);
        this.stock = stock;
    }

    private void validateNotNegative(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("재고는 음수가 될 수 없습니다.");
        }
    }

    public int getStock() {
        return stock;
    }
}
