package cart.cart.entity;

public class CartProductEntity {
    private final int id;
    private final String productName;
    private final int productPrice;
    private final String productImage;

    public CartProductEntity(final int id, final String productName, final int productPrice, final String productImage) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}
