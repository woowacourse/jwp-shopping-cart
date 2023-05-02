package cart.domain;

public class Cart {
    private final long productId;
    private final long memberId;
    private int count;

    public Cart(long productId, long memberId, int count) {
        this.productId = productId;
        this.memberId = memberId;
        this.count = count;
    }

    public long getProductId() {
        return productId;
    }

    public long getMemberId() {
        return memberId;
    }

    public int getCount() {
        return count;
    }
}
