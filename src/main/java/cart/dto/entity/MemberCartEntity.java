package cart.dto.entity;

public class MemberCartEntity {
    private final Long productId;
    private final Long memberId;
    private final String productName;
    private final String productImage;
    private final Integer productPrice;

    public MemberCartEntity(Long productId, Long memberId, String productName, String productImage, Integer product_price) {
        this.productId = productId;
        this.memberId = memberId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = product_price;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getMemberId() {
        return memberId;
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
