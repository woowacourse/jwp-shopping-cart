package cart.entity;

public class CartEntity {

    private final Integer id;
    private final Integer memberId;
    private final Integer productId;

    public CartEntity(final Integer id, final Integer memberId, final Integer productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public Integer getProductId() {
        return productId;
    }

}
