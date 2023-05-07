package cart.service.cart.domain;

import cart.service.product.domain.Product;

public class CartItem {
    private final Product product;

    public CartItem(Product product) {
        this.product = product;
    }

    public long getProductId() {
        return product.getId();
    }

    public String getProductName() {
        return product.getName();
    }

    public String getProductImageUrl() {
        return product.getImageUrl();
    }

    public int getProductPrice() {
        return product.getPrice();
    }
}
