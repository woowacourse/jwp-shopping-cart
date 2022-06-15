package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class CartItem {

    private final Long id;
    private final Long memberId;
    private final Product product;
    private int quantity;

    public CartItem(Long memberId, Product product, int quantity) {
        this(null, memberId, product, quantity);
    }

    public CartItem(Long id, Long memberId, Product product, int quantity) {
        validateQuantity(product, quantity);
        this.id = id;
        this.memberId = memberId;
        this.product = product;
        this.quantity = quantity;
    }

    private void validateQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("상품 수량은 0보다 커야 합니다.");
        }
        product.validateStock(quantity);
    }

    public void addQuantity(int quantity) {
        validateQuantity(this.product, quantity);
        this.quantity += quantity;
    }

    public void replaceQuantity(int quantity) {
        if (quantity == 0) {
            this.quantity = quantity;
            return;
        }
        validateQuantity(this.product, quantity);
        this.quantity = quantity;
    }

    public boolean isEmpty() {
        return this.quantity == 0;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id) && Objects.equals(memberId, cartItem.memberId)
                && Objects.equals(product, cartItem.product) && Objects.equals(quantity,
                cartItem.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memberId, product, quantity);
    }
}
