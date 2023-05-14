package cart.entity;

public class CartEntity {

    private final Integer id;
    private final int memberId;
    private final int productId;

    public CartEntity(final Integer id, final int memberId, final int productId) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
    }

    public CartEntity(final int memberId, final int productId) {
        this(null, memberId, productId);
    }

    public Integer getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getProductId() {
        return productId;
    }

}
