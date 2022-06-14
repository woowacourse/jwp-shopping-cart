package woowacourse.shoppingcart.cartitem.domain;

import woowacourse.shoppingcart.product.domain.Product;

public class CartItem {

    private Long id;
    private Product product;
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(Long id, Product product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
