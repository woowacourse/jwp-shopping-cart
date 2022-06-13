package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidCartItemException;

public class CartItem {

    private final Long id;
    private final Product product;
    private final Integer quantity;
    private final Boolean checked;

    public CartItem(Long id, Product product, Integer quantity, Boolean checked) {
        validateNull(product, quantity, checked);
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.checked = checked;
    }

    private void validateNull(Product product, Integer quantity, Boolean checked) {
        if (quantity == null || checked == null) {
            throw new InvalidCartItemException();
        }
    }

    public CartItem(Product product, Integer quantity, Boolean checked) {
        this(null, product, quantity, checked);
    }

    public CartItem(Long id, Integer quantity, Boolean checked) {
        this(id, null, quantity, checked);
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

    public boolean isChecked() {
        return checked;
    }
}
