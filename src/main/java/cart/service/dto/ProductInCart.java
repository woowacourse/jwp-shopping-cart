package cart.service.dto;

public class ProductInCart {

    private final long id;
    private final String productName;
    private final int productPrice;
    private final String productImageUrl;

    public ProductInCart(
            final long id,
            final String productName,
            final int productPrice,
            final String productImageUrl
    ) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageUrl = productImageUrl;
    }

    public long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }
}
