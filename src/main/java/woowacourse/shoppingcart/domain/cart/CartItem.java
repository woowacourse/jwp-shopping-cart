package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.domain.product.Product;

public class CartItem {

    private Long id;
    private Product product;

    public CartItem() {
    }

    public CartItem(Long id, Product product) {
        this.id = id;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getName() {
        return product.getName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }
}
