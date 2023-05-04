package cart.entity;

public class PutCart {

    private final Integer memberId;
    private final Integer productId;

    public PutCart(Integer memberId, Integer productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public Integer getProductId() {
        return productId;
    }
}
