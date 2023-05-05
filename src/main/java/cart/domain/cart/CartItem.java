package cart.domain.cart;

public class CartItem {
    
    private final Long id;
    private final String productName;
    private final int productPrice;
    private final String productImage;

    public CartItem(final Long id, final String productName, final int productPrice, final String productImage) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public Long getId() {
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
