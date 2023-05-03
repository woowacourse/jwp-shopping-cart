package cart.business.domain.cart;

public class CartItem {

    private final Integer id;
    private final Integer productId;
    private final Integer memberId;

    public CartItem(Integer id, Integer productId, Integer memberId) {
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
