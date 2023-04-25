package cart.domain;

public class Product {

    private final ProductId productId;
    private final ProductName productName;
    private final ProductImage productImage;
    private final ProductPrice productPrice;

    public Product(final ProductId productId, final ProductName productName, final ProductImage productImage, final ProductPrice productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
    }

    public ProductId getProductId() {
        return productId;
    }

    public ProductName getProductName() {
        return productName;
    }

    public ProductImage getProductImage() {
        return productImage;
    }

    public ProductPrice getProductPrice() {
        return productPrice;
    }
}
