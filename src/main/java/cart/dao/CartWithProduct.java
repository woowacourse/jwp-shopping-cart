package cart.dao;

public class CartWithProduct {
    private final Integer memberId;
    private final Integer productId;
    private final String productName;
    private final String productImage;
    private final Integer productPrice;

    public CartWithProduct(final Integer memberId, final Integer productId, final String productName, final String productImage, final Integer productPrice) {
        this.memberId = memberId;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public Integer getProductPrice() {
        return productPrice;
    }
}
