package cart.controller.dto;

public class CartDto {

    private final long memberId;
    private final long productId;
    private final String productName;
    private final String productImageUrl;
    private final int productPrice;
    private final String productCategory;

    public CartDto(final long memberId, final long productId, final String productName,
                   final String productImageUrl,
                   final int productPrice, final String productCategory) {
        this.memberId = memberId;
        this.productId = productId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
    }

    public long getMemberId() {
        return memberId;
    }

    public long getProductId() {
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
