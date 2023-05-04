package cart.dao;

public class CartEntity {

    private final Integer id;
    private final Integer productId;
    private final Integer memberId;

    public CartEntity(final Integer productId, final Integer memberId) {
        this(null, productId, memberId);
    }

    public CartEntity(final Integer id, final Integer productId, final Integer memberId) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getMemberId() {
        return memberId;
    }

}
