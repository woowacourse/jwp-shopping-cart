package cart.domain.cart;

import cart.domain.product.Product;
import cart.domain.product.ProductImage;
import cart.domain.product.ProductName;
import cart.domain.product.ProductPrice;

public class CartItem {

    private final Long id;
    private final ProductName productName;
    private final ProductPrice productPrice;
    private final ProductImage productImage;

    public CartItem(final Long id, final Product product) {
        this(id, product.getName(), product.getPrice(), product.getImage());
    }

    public CartItem(final Long id, final String productName, final int productPrice, final String productImage) {
        this.id = id;
        this.productName = new ProductName(productName);
        this.productPrice = new ProductPrice(productPrice);
        this.productImage = new ProductImage(productImage);
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName.getValue();
    }

    public int getProductPrice() {
        return productPrice.getValue();
    }

    public String getProductImage() {
        return productImage.getValue();
    }
}
