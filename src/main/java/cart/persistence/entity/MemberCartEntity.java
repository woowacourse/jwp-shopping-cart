package cart.persistence.entity;

public class MemberCartEntity {
    private final Long cartId;
    private final Long memberId;
    private final Long productId;
    private final String productName;
    private final String productImageUrl;
    private final int productPrice;
    private final String productCategory;

    public MemberCartEntity(final Long cartId, final Long memberId, final Long productId, final String productName,
                            final String productImageUrl, final int productPrice, final String productCategory) {
        this.cartId = cartId;
        this.memberId = memberId;
        this.productId = productId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }
}
