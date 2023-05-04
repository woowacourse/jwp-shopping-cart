package cart.domain;

public class Cart {
    private final long productId;
    private int count;

    public Cart(long productId, int count) {
        this.productId = productId;
        this.count = count;
    }

    public long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {
        this.count++;
    }
}
