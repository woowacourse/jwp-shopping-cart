package cart.domain.cart;

import cart.domain.member.Member;
import cart.domain.product.Product;

public class Cart {

    private static final int MIN_QUANTITY = 1;

    private final Long id;
    private final long memberId;
    private final long productId;
    private int quantity;

    public Cart(final long memberId, final long productId) {
        this(null, memberId, productId, 1);
    }

    public Cart(final Long id, final long memberId, final long productId, final int quantity) {
        validate(quantity);
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public static Cart of(final Member member, final Product product) {
        return new Cart(member.getId(), product.getId());
    }

    public void validate(final int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException("상품의 최소 수량은 " + MIN_QUANTITY + "개 입니다.");
        }
    }

    public void plusQuantity() {
        this.quantity += 1;
    }

    public Long getId() {
        return id;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
