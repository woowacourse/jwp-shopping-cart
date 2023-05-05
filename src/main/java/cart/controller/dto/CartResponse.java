package cart.controller.dto;

public class CartResponse {
    private final Long id;
    private final String productImageUrl;
    private final String productName;
    private final int productPrice;

    public CartResponse(Long id, String productImageUrl, String productName, int productPrice) {
        this.id = id;
        this.productImageUrl = productImageUrl;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Long getId() {
        return id;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }
}
