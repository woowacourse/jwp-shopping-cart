package woowacourse.shoppingcart.domain.cart;

import java.util.Objects;

import woowacourse.shoppingcart.domain.Quantity;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.domain.product.ProductStock;
import woowacourse.shoppingcart.exception.DuplicateProductInCartException;
import woowacourse.shoppingcart.exception.OutOfStockException;

public class CartItem {
    private Long id;
    private Product product;
    private Quantity quantity;

    public CartItem(Product product, Quantity quantity) {
        this(null, product, quantity);
    }

    public CartItem(Long id, Product product, Quantity quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public void checkSameProduct(CartItem newCartItem) {
        if (product.equals(newCartItem.product)) {
            throw new DuplicateProductInCartException();
        }
    }

    public void checkEnoughStockToOrder(ProductStock productStock) {
        if (productStock.getStockQuantity() < getQuantity()) {
            throw new OutOfStockException();
        }
    }

    public long getProductId() {
        return product.getId();
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity.getQuantity();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CartItem))
            return false;
        CartItem cartItem = (CartItem)o;
        return getId().equals(cartItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
